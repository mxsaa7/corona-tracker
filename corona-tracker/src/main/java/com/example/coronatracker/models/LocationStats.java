package com.example.coronatracker.models;

public class LocationStats {
    public int latest_cases;
    public String province; 
    public String country;
    public String city;


    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public int getLatestCases() {
        return latest_cases;
    }
    public void setLatestCases(int latest_cases) {
        this.latest_cases = latest_cases;
    }
    public String getProvince() {
        return province;
    }
    public void setProvince(String province) {
        if(province == null || province == " "){
            province = "N/A";
        }
        else{
            this.province = province;
        }
        
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString(){
        return "{" + 
                    "country='" + this.country + "'\n" + 
                    "state='" + this.province + "'\n" +
                    "latest_total_cases='" + this.latest_cases + 
                "'}\n" 
                    ;

    }


    
}
