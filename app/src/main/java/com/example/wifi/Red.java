package com.example.wifi;

public class Red {
    private int idNetworkId;
    private String SSID;
    private String BSSID;
     public Red( int idNetworkId,String SSID,String BSSID)
     {
         this.idNetworkId=idNetworkId;
         this.SSID=SSID;
         this.BSSID=BSSID;
     }

    public int getIdNetworkId() {
        return idNetworkId;
    }

    public void setIdNetworkId(int idNetworkId) {
        this.idNetworkId = idNetworkId;
    }

    public String getSSID() {
        return SSID;
    }

    public void setSSID(String SSID) {
        this.SSID = SSID;
    }

    public String getBSSID() {
        return BSSID;
    }

    public void setBSSID(String BSSID) {
        this.BSSID = BSSID;
    }
}
