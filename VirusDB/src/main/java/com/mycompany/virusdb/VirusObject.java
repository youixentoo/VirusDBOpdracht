/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virusdb;

import java.util.HashSet;
import java.util.Set;

/**
 * [114871, Zygosaccharomyces bailii virus Z, Viruses; Riboviria; Amalgaviridae;
 * Zybavirus, NC_003874, null, null, null, 4954, Zygosaccharomyces bailii,
 * Eukaryota; Fungi; Dikarya; Ascomycota; Saccharomycotina; Saccharomycetes;
 * Saccharomycetales; Saccharomycetaceae; Zygosaccharomyces, null, Literature,
 * null, null]
 *
 * @author gebruiker
 */
public class VirusObject implements Comparable<VirusObject> {

    private int virusTaxID;
    private String virusName;
    private String virusLineage;
    private String refSeqID;
    private String KEGGGenome;
    private String KEGGDisease;
    private String disease;
    private int hostTaxID;
    private String hostName;
    private String hostLineage;
    private String pmid;
    private String evidence;
    private String classification;
    private int numOfHosts = 0;
    private Set<Integer> hosts;

    public VirusObject() {
    }

    public VirusObject(int virusTaxID, String virusName, String virusLineage, String refSeqID, String KEGGGenome, String KEGGDisease, String disease, int hostTaxID, String hostName, String hostLineage, String pmid, String evidence, String classification) {
        this.virusTaxID = virusTaxID;
        this.virusName = virusName;
        this.virusLineage = virusLineage;
        this.refSeqID = refSeqID;
        this.KEGGGenome = KEGGGenome;
        this.KEGGDisease = KEGGDisease;
        this.disease = disease;
        this.hostTaxID = hostTaxID;
        this.hostName = hostName;
        this.hostLineage = hostLineage;
        this.pmid = pmid;
        this.evidence = evidence;
        this.classification = classification;
        this.hosts = new HashSet<>();
    }

    public VirusObject(String[] row) {
        this.virusTaxID = Integer.parseInt(row[0]);
        this.virusName = row[1];
        this.virusLineage = row[2];
        this.refSeqID = row[3];
        this.KEGGGenome = row[4];
        this.KEGGDisease = row[5];
        this.disease = row[6];
        setHostID(row[7]);
        this.hostName = row[8];
        this.hostLineage = row[9];
        this.pmid = row[10];
        this.evidence = row[11];
        this.hosts = new HashSet<>();

        setClassification();
    }

    private void setHostID(String rowIndex) {
        try {
            this.hostTaxID = Integer.parseInt(rowIndex);
        } catch (Exception exc) {
            if (rowIndex == null) {
                this.hostTaxID = 0;
            }
        }
    }

    private void setClassification() {
        String data = virusLineage.toLowerCase();

        if (data.contains("ssrna")) {
            classification = "ssRNA";
        } else if (data.contains("dsrna")) {
            classification = "dsRNA";
        } else if (data.contains("ssdna")) {
            classification = "ssDNA";
        } else if (data.contains("dsdna")) {
            classification = "dsDNA";
        } else if (data.contains("retro")) {
            classification = "Retrovirus";
        } else if (data.contains("satellite")) {
            classification = "Satellite";
        } else if (data.contains("viroid")) {
            classification = "Viroid";
        } else {
            if (virusName.contains("virophage")) {
                classification = "Virophage";
            } else {
                classification = "Unknown";
            }
        }
    }
    
    public void addToHosts(Integer hostID){
        this.hosts.add(hostID);
    }
    
    public void setNumOfHosts(int amount){
        this.numOfHosts = amount;
    }
    
    public void increaseNumOfHosts(){
        this.numOfHosts += 1;
    }

    public int getVirusTaxID() {
        return virusTaxID;
    }

    public String getVirusName() {
        return virusName;
    }

    public String getVirusLineage() {
        return virusLineage;
    }

    public String getRefSeqID() {
        return refSeqID;
    }

    public String getKEGGGenome() {
        return KEGGGenome;
    }

    public String getKEGGDisease() {
        return KEGGDisease;
    }

    public String getDisease() {
        return disease;
    }

    public int getHostTaxID() {
        return hostTaxID;
    }

    public String getHostName() {
        return hostName;
    }

    public String getHostLineage() {
        return hostLineage;
    }

    public String getPmid() {
        return pmid;
    }

    public String getEvidence() {
        return evidence;
    }

    public String getClassification() {
        return classification;
    }

    public int getNumOfHosts() {
        return numOfHosts;
    }

    public Set<Integer> getHosts() {
        return hosts;
    }
    
    

    public String getHostIDandName() {
        return String.format("%s; (%s)", this.hostTaxID, this.hostName);
    }

    @Override
    public String toString() {
        return String.valueOf(this.virusTaxID);
    }

    @Override
    public int compareTo(VirusObject o) {
        return this.classification.compareTo(o.classification);
    }

}
