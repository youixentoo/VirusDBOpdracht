/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virusdb;

import com.univocity.parsers.common.ParsingContext;
import com.univocity.parsers.common.processor.ObjectRowProcessor;
import com.univocity.parsers.conversions.Conversions;
import com.univocity.parsers.tsv.TsvParser;
import com.univocity.parsers.tsv.TsvParserSettings;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;
import javax.swing.ListModel;

/**
 *
 * @author gebruiker
 */
public class VirusDBFunctions {

    public static List<VirusObject> processTSVFile(File file) throws FileNotFoundException {
        TsvParserSettings settings = new TsvParserSettings();
        List<VirusObject> virusList = new ArrayList<>();

        ObjectRowProcessor rowProcessor = new ObjectRowProcessor() {
            @Override
            public void rowProcessed(Object[] row, ParsingContext context) {
                //here is the row. Let's just print it.
                //System.out.println(Arrays.toString(row));

                // Casting to String[] doesnt work
                String[] stringRow = Arrays.copyOf(row, row.length, String[].class);

                try {
                    virusList.add(new VirusObject(stringRow));
                } catch (Exception exc) {
                    //System.out.println(exc);
                }
            }
        };

        settings.setProcessor(rowProcessor);
        TsvParser parser = new TsvParser(settings);
        parser.parse(file);

        // parses all rows in one go.
        //List<String[]> allRows = parser.parseAll(new FileReader(filePath));
        return virusList;
    }
    
    public static List<VirusObject> processTSVFile(InputStreamReader isp) {
        TsvParserSettings settings = new TsvParserSettings();
        List<VirusObject> virusList = new ArrayList<>();

        ObjectRowProcessor rowProcessor = new ObjectRowProcessor() {
            @Override
            public void rowProcessed(Object[] row, ParsingContext context) {
                //here is the row. Let's just print it.
                //System.out.println(Arrays.toString(row));

                // Casting to String[] doesnt work
                String[] stringRow = Arrays.copyOf(row, row.length, String[].class);

                try {
                    virusList.add(new VirusObject(stringRow));
                } catch (Exception exc) {
                    //System.out.println(exc);
                }
            }
        };

        settings.setProcessor(rowProcessor);
        TsvParser parser = new TsvParser(settings);
        parser.parse(isp);

        // parses all rows in one go.
        //List<String[]> allRows = parser.parseAll(new FileReader(filePath));
        return virusList;
    }

    public static List<String> getVirusClasses(List<VirusObject> virusList) {
        Set virusClasses = new HashSet();
        for (VirusObject virus : virusList) {
            virusClasses.add(virus.getClassification());
        }

        return new ArrayList<>(virusClasses);
    }

    public static Map<Integer, String> getHostComboItems(List<VirusObject> virusList, String option) {
        Map<Integer, String> hostIDandName = new HashMap<>();

        if (option.equals("Any")) {
            for (VirusObject virus : virusList) {
                hostIDandName.put(virus.getHostTaxID(), virus.getHostName());
            }
        } else {
            for (VirusObject virus : virusList) {
                if (option.equals(virus.getClassification())) {
                    hostIDandName.put(virus.getHostTaxID(), virus.getHostName());
                }
            }
        }

        return hostIDandName;
    }

    public static Map<Integer, List<VirusObject>> getHostVirusMap(List<VirusObject> virusList) {
        Map<Integer, List<VirusObject>> hostVirusMap = new HashMap<>();

        for (VirusObject virus : virusList) {
            if (hostVirusMap.containsKey(virus.getHostTaxID())) {
                List value = hostVirusMap.get(virus.getHostTaxID());
                value.add(virus);
                hostVirusMap.put(virus.getHostTaxID(), value);
            } else {
                List value = new ArrayList();
                value.add(virus);
                hostVirusMap.put(virus.getHostTaxID(), value);
            }

        }

        return hostVirusMap;
    }

    public static List<VirusObject> getHostVirusIDs(Map<Integer, List<VirusObject>> hostVirusMap, int hostID) {
        List<VirusObject> hostVirusList = hostVirusMap.get(hostID);

        return hostVirusList;
    }

    public static String[] convertVirusObjecttoStringArray(List<VirusObject> hostVirusList) {
        //https://stackoverflow.com/questions/4581407/how-can-i-convert-arraylistobject-to-arrayliststring
        List<String> hostVirusses = hostVirusList.stream().map(virus -> virus.toString()).collect(Collectors.toList());
        return hostVirusses.stream().toArray(String[]::new);
    }

    public static List<VirusObject> getSortedVirusIDs(List<VirusObject> hostVirusses, String option) {

        if (option.equals("NUM")) {
            Collections.sort(hostVirusses, Comparator.comparingInt(VirusObject::getNumOfHosts));
        } else if (option.equals("CLASS")) {
            Collections.sort(hostVirusses, Comparator.comparing(VirusObject::getClassification));
        } else { //ID
            Collections.sort(hostVirusses, Comparator.comparingInt(VirusObject::getVirusTaxID));
        }

        return hostVirusses;
    }

    public static List<VirusObject> setNumOfHosts(List<VirusObject> virusList, Map<Integer, List<VirusObject>> hostVirusses) {

        Map<VirusObject, Integer> hostCounts = new HashMap<>();

        


        //System.out.println(hostVirusses.keySet());
//        int trues = 0;
//        int falses = 0;
//
//        for (VirusObject virus : virusList) {
//            if (hostVirusses.containsKey(virus.getHostTaxID())) {
//                virus.increaseNumOfHosts();
//                System.out.println(hostVirusses.containsKey(virus.getHostTaxID()));
//                System.out.println(virus.getHostTaxID());
//            }
//        }
//        for(int key:hostVirusses.keySet()){
//            for(VirusObject virus:virusList){
//                if(findInVirusList(hostVirusses.get(key), virus)){
//                    virus.addToHosts(key);
//                    trues++;
//                }else{
//                    falses++;
//                }
//            }
//        }
//        for(VirusObject virus:virusList){
//            for(int key:hostVirusses.keySet()){
//                if(findInVirusList(hostVirusses.get(key), virus)){
//                    virus.increaseNumOfHosts();
//                    trues++;
//                }else{
//                    falses++;
//                }
//            }
//        }
//        System.out.println(hostVirusses.keySet().size());
//        System.out.println(trues);
//        System.out.println(falses);
//        System.out.println(virusList.size());
//        for (VirusObject virus : virusList) {
//            System.out.println(virus.getNumOfHosts());
//        }
        return virusList;

    }

    private static boolean findInVirusList(List<VirusObject> listVirus, VirusObject virus) {
        for (VirusObject v : listVirus) {
            if (v.getHostTaxID() == virus.getHostTaxID()) {
                return true;
            }
        }
        return false;

        //return listVirus.stream().filter(o -> o.getHostTaxID() == virus.getHostTaxID()).findFirst().isPresent();
    }

    public static List<Integer> getIntersection(List<VirusObject> list1Data, List<VirusObject> list2Data) {
        Set<Integer> set1Data = new LinkedHashSet<>(list1Data.stream().map(virus -> virus.getVirusTaxID()).collect(Collectors.toList()));
        Set<Integer> set2Data = new LinkedHashSet<>(list2Data.stream().map(virus -> virus.getVirusTaxID()).collect(Collectors.toList()));

        set1Data.retainAll(set2Data);

        return Arrays.asList(set1Data.stream().toArray(Integer[]::new));
    }

}
