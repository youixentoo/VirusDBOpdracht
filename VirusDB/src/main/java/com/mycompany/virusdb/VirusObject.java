/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virusdb;

/**
 *
 * @author gebruiker
 */
public class VirusObject{

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

    /**
     *
     */
    public VirusObject() {
    }

    /**
     *
     * @param virusTaxID
     * @param virusName
     * @param virusLineage
     * @param refSeqID
     * @param KEGGGenome
     * @param KEGGDisease
     * @param disease
     * @param hostTaxID
     * @param hostName
     * @param hostLineage
     * @param pmid
     * @param evidence
     * @param classification
     */
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
    }

    /**
     *
     * @param row
     */
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
    
    /**
     *
     * @param amount
     */
    public void setNumOfHosts(int amount){
        this.numOfHosts = amount;
    }

    /**
     *
     * @return
     */
    public int getVirusTaxID() {
        return virusTaxID;
    }
    
    /**
     *
     * @return
     */
    public String getVirusName() {
        return virusName;
    }
    
    /**
     *
     * @return
     */
    public String getVirusLineage() {
        return virusLineage;
    }
    
    /**
     *
     * @return
     */
    public String getRefSeqID() {
        return refSeqID;
    }
    
    /**
     *
     * @return
     */
    public String getKEGGGenome() {
        return KEGGGenome;
    }
    
    /**
     *
     * @return
     */
    public String getKEGGDisease() {
        return KEGGDisease;
    }
    
    /**
     *
     * @return
     */
    public String getDisease() {
        return disease;
    }
    
    /**
     *
     * @return
     */
    public int getHostTaxID() {
        return hostTaxID;
    }
    
    /**
     *
     * @return
     */
    public String getHostName() {
        return hostName;
    }
    
    /**
     *
     * @return
     */
    public String getHostLineage() {
        return hostLineage;
    }
    
    /**
     *
     * @return
     */
    public String getPmid() {
        return pmid;
    }
    
    /**
     *
     * @return
     */
    public String getEvidence() {
        return evidence;
    }
    
    /**
     *
     * @return
     */
    public String getClassification() {
        return classification;
    }
    
    /**
     *
     * @return
     */
    public int getNumOfHosts() {
        return numOfHosts;
    }
    
    /**
     *
     * @return
     */
    public String getHostIDandName() {
        return String.format("%s; (%s)", this.hostTaxID, this.hostName);
    }

    @Override
    public String toString() {
        return String.valueOf(this.virusTaxID);
    }
}
