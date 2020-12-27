package com.sparta.training4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class SearchInFile_v0_1 {

    String content;
    List<String> wordsFromFile;
    Map<String, Integer> searchResults = new HashMap<>();
    int topResultsCount;
    Map<String, Integer> sortedMap = new LinkedHashMap<>();

    public SearchInFile_v0_1(int topResultsCount) {
        this.topResultsCount = topResultsCount;
    }

    public static void main(String[] args) throws IOException {

        String filePath = "C:\\Users\\sergeyzubets\\IdeaProjects\\trainig\\sparta\\src\\main\\resources\\inputFile.txt";

        SearchInFile_v0_1 searchInfile1 = new SearchInFile_v0_1(5);
        searchInfile1.content = new String(Files.readAllBytes(Paths.get(filePath)));

        // очищаем текст от спец. символов
        searchInfile1.removeExtraSymbols();
        System.out.println("Текст без спец. символов:"+(searchInfile1.content));

        // вызываем метод поиска и отдаем туда массив
        searchInfile1.searchByWord();

        // выводим топ ${topResultsCount} самых часто встречающихся слов в тексте
        System.out.println("Весь не отсортированный мап:"+searchInfile1.searchResults);
        searchInfile1.displayTopResults();

    } // end main

    public void removeExtraSymbols(){
        this.content = this.content.toLowerCase(); // поиск не чувствителен к регистру
        this.content = this.content
                .replaceAll("[[0-9]|.,@#$%^&*()_+!№;:?/~\\\"]","")     // удаляем знаки препинания кроме тире
                .replaceAll("\\s-\\s", " ")                            // удаляем тире между словами
                .replaceAll("-\\s", " ")
                .replaceAll("\\s-", " ")
                .replaceAll("\\s+", " ");                              // удаляем множественные пробелы
    }

    public void searchByWord(){

        this.wordsFromFile = new ArrayList<String>(Arrays.asList(this.content.split("\\s")));

        //удаляем пустые ячейки в массиве
        for (int removeMeId = 0; removeMeId < this.wordsFromFile.size(); removeMeId++) {
            if (this.wordsFromFile.get(removeMeId).equals("")) {
                this.wordsFromFile.remove(removeMeId);
            }
        }

        // подсчет повторений слов в тексте
        for (String word : this.wordsFromFile) {
            if (this.searchResults.containsKey(word)) {
                this.searchResults.put(word, this.searchResults.get(word) + 1);
            } else {
                this.searchResults.put(word, 1);
            }
        }
        // вариант 2
//        for (String word : this.wordsFromFile){
//            Integer count = this.searchResults.get(word);
//            if (count == null) {
//                count = 0;
//            }
//            this.searchResults.put(word, count +=1);
//        }
    }

    public void displayTopResults() {

        // преобразование исходного мапа в отсортированный
        this.searchResults.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEachOrdered(x -> this.sortedMap.put(x.getKey(), x.getValue()));

        System.out.println("   Весь отсортированный мап:"+this.sortedMap);
        int i = 0;
        for (Map.Entry<String,Integer> entry : this.sortedMap.entrySet())
            if (i != this.topResultsCount) {
                System.out.println("Слово '" + entry.getKey() + "' встретилось в тексте " + entry.getValue()+" раз");
                i++;
            } else
                break;
    }
}