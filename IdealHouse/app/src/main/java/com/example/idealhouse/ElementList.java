package com.example.idealhouse;

import android.database.Cursor;

public class ElementList {
        //cargar desde API
    public ElementList() {
        this.propertyCode="87887676";
        this.thumbnail= "https://img3.idealista.com/blur/WEB_LISTING/0/id.pro.es.image.master/87/9c/2b/739041713.jpg";
        this.externalReference= "CAC-79433";
        this.numPhotos=14;
        this.price= 450000;
        this.propertyType ="chalet";
        this.operation= "sale";
        this.size=850;
        this.rooms = 12;
        this.bathrooms= 8;
        this.address= "Avenida de Cervantes";
        this.province= "Cáceres";
        this.municipality= "Cañamero";
        this.country= "es";
        this.latitude= 39.3815505;
        this.longitude= -5.3913332;
        this.showAddress= false;
        this.url= "https://www.idealista.com/inmueble/87887676/";
        this.distance= "9449";
        this.hasVideo= false;
        this.status= "good";
        this.newDevelopment= false;
    }

    public ElementList ElementList2() {

        this.propertyCode="87887676";
        this.thumbnail= "https://img3.idealista.com/blur/WEB_LISTING/0/id.pro.es.image.master/81/28/2b/585849676.jpg";
        this.externalReference= "CAC-79433";
        this.numPhotos=14;
        this.price= 450000;
        this.propertyType ="chalet";
        this.operation= "sale";
        this.size=850;
        this.rooms = 12;
        this.bathrooms= 8;
        this.address= "Avenida de Cervantes";
        this.province= "Cáceres";
        this.municipality= "Cañamero";
        this.country= "es";
        this.latitude= 39.3815505;
        this.longitude= -5.3913332;
        this.showAddress= false;
        this.url= "https://www.idealista.com/inmueble/87887676/";
        this.distance= "9449";
        this.hasVideo= false;
        this.status= "good";
        this.newDevelopment= false;
        return this;
    }

       // this.thumbnail="https://img3.idealista.com/blur/WEB_LISTING/0/id.pro.es.image.master/81/28/2b/585849676.jpg";
      //  this.url="https://www.idealista.com/inmueble/81475621/";


    // crear anuncio pantalla
    public ElementList(int id,String name,String telefono,long prize,long meters,String desc,int type,byte[] image,double latitude,double longitude){
        this.id=id;
        this.setName(name);
        this.setTelephone(telefono);
        this.setPrice(prize);
        this.setSize(meters);
        this.setDescription(desc);
        this.setTipoAnuncio(type);
        this.setImage(image);
        this.setLatitude(latitude);
        this.setLongitude(longitude);
    }

    public ElementList(Cursor cursor){
        this.id=cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLS[0]));
        this.setName(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLS[1])));
        this.setTelephone(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLS[2])));
        this.setPrice(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.COLS[3])));
        this.setSize(cursor.getLong(cursor.getColumnIndex(DataBaseHelper.COLS[4])));
        this.setDescription(cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLS[5])));
        this.setTipoAnuncio(cursor.getInt(cursor.getColumnIndex(DataBaseHelper.COLS[6])));
        this.setImage(cursor.getBlob(cursor.getColumnIndex(DataBaseHelper.COLS[7])));
        this.setLatitude(cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.COLS[8])));
        this.setLongitude(cursor.getDouble(cursor.getColumnIndex(DataBaseHelper.COLS[9])));
        this.propertyCode=null;
    }

    private String propertyCode;
    private String thumbnail;
    private String externalReference;
    private long numPhotos;
    private long price;
    private String propertyType;
    private String operation;
    private long size;
    private boolean exterior;
    private long rooms;
    private long bathrooms;
    private String address;
    private String province;
    private String municipality;
    private String country;
    private double latitude;
    private double longitude;
    private boolean showAddress;
    private String url;
    private String distance;
    private boolean hasVideo;
    private String status;
    private boolean newDevelopment;
    private String parkingSpace;
    private long priceByArea;
    private String detailedType;
    private String suggestedTexts;
    private boolean hasPlan;
    private boolean has3DTour;
    private boolean has360;
    private Boolean hasLift;
    private String floor;
    //creadas
    private String Telephone;
    private String Name;
    private int TipoAnuncio;
    private int id;
    private String Description;
    private byte[] Image;


    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }
    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTipoAnuncio() {
        return TipoAnuncio;
    }

    public void setTipoAnuncio(int tipoAnuncio) {
        TipoAnuncio = tipoAnuncio;
    }



    public String getTelephone() {
        return Telephone;
    }

    public void setTelephone(String telephone) {
        Telephone = telephone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getExternalReference() {
        return externalReference;
    }

    public void setExternalReference(String externalReference) {
        this.externalReference = externalReference;
    }

    public long getNumPhotos() {
        return numPhotos;
    }

    public void setNumPhotos(long numPhotos) {
        this.numPhotos = numPhotos;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public boolean isExterior() {
        return exterior;
    }

    public void setExterior(boolean exterior) {
        this.exterior = exterior;
    }

    public long getRooms() {
        return rooms;
    }

    public void setRooms(long rooms) {
        this.rooms = rooms;
    }

    public long getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(long bathrooms) {
        this.bathrooms = bathrooms;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public boolean isShowAddress() {
        return showAddress;
    }

    public void setShowAddress(boolean showAddress) {
        this.showAddress = showAddress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public boolean isHasVideo() {
        return hasVideo;
    }

    public void setHasVideo(boolean hasVideo) {
        this.hasVideo = hasVideo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isNewDevelopment() {
        return newDevelopment;
    }

    public void setNewDevelopment(boolean newDevelopment) {
        this.newDevelopment = newDevelopment;
    }

    public String getParkingSpace() {
        return parkingSpace;
    }

    public void setParkingSpace(String parkingSpace) {
        this.parkingSpace = parkingSpace;
    }

    public long getPriceByArea() {
        return priceByArea;
    }

    public void setPriceByArea(long priceByArea) {
        this.priceByArea = priceByArea;
    }

    public String getDetailedType() {
        return detailedType;
    }

    public void setDetailedType(String detailedType) {
        this.detailedType = detailedType;
    }

    public String getSuggestedTexts() {
        return suggestedTexts;
    }

    public void setSuggestedTexts(String suggestedTexts) {
        this.suggestedTexts = suggestedTexts;
    }

    public boolean isHasPlan() {
        return hasPlan;
    }

    public void setHasPlan(boolean hasPlan) {
        this.hasPlan = hasPlan;
    }

    public boolean isHas3DTour() {
        return has3DTour;
    }

    public void setHas3DTour(boolean has3DTour) {
        this.has3DTour = has3DTour;
    }

    public boolean isHas360() {
        return has360;
    }

    public void setHas360(boolean has360) {
        this.has360 = has360;
    }

    public Boolean getHasLift() {
        return hasLift;
    }

    public void setHasLift(Boolean hasLift) {
        this.hasLift = hasLift;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }


    @Override
    public String toString() {
        return "Name: '" + this.province ;
    }


}
