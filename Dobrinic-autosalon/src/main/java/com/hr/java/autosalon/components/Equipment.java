package com.hr.java.autosalon.components;

/**
 * Equipment
 */
public class Equipment extends Entity{
    private boolean leather;
    private boolean airConditioning;
    private boolean parkingAssist;
    private boolean bluetooth;
    private boolean ambientLights;
    private boolean shiftPaddles;
    private boolean voiceControl;

    public Equipment(Long ID,EquipmentBuilder builder){
        super(ID);
        this.leather = builder.leather;
        this.airConditioning = builder.airConditioning;
        this.parkingAssist = builder.parkingAssist;
        this.bluetooth = builder.bluetooth;
        this.ambientLights = builder.ambientLights;
        this.shiftPaddles = builder.shiftPaddles;
        this.voiceControl = builder.voiceControl;
    }

    @Override
    public String toString() {
        return "Equipment{" +
                "leather=" + leather +
                ", airConditioning=" + airConditioning +
                ", parkingAssist=" + parkingAssist +
                ", bluetooth=" + bluetooth +
                ", ambientLights=" + ambientLights +
                ", shiftPaddles=" + shiftPaddles +
                ", voiceControl=" + voiceControl +
                "}\n";
    }

    public String getListofEquipment(){
        StringBuilder builder = new StringBuilder();
        if(leather){
            builder.append("Leather\n");
        }
        if(airConditioning){
            builder.append("AirCon\n");
        }
        if(parkingAssist){
            builder.append("Parking Assist\n");
        }
        if(bluetooth){
            builder.append("Bluetooth\n");
        }
        if(ambientLights){
            builder.append("Ambient Lights\n");
        }
        if(shiftPaddles){
            builder.append("Shift Paddles\n");
        }
        if(voiceControl){
            builder.append("Voice Control\n");
        }
        return builder.toString();
    }

    public boolean isLeather() {
        return leather;
    }

    public void setLeather(boolean leather) {
        this.leather = leather;
    }

    public boolean isAirConditioning() {
        return airConditioning;
    }

    public void setAirConditioning(boolean airConditioning) {
        this.airConditioning = airConditioning;
    }

    public boolean isParkingAssist() {
        return parkingAssist;
    }

    public void setParkingAssist(boolean parkingAssist) {
        this.parkingAssist = parkingAssist;
    }

    public boolean isBluetooth() {
        return bluetooth;
    }

    public void setBluetooth(boolean bluetooth) {
        this.bluetooth = bluetooth;
    }

    public boolean isAmbientLights() {
        return ambientLights;
    }

    public void setAmbientLights(boolean ambientLights) {
        this.ambientLights = ambientLights;
    }

    public boolean isShiftPaddles() {
        return shiftPaddles;
    }

    public void setShiftPaddles(boolean shiftPaddles) {
        this.shiftPaddles = shiftPaddles;
    }

    public boolean isVoiceControl() {
        return voiceControl;
    }

    public void setVoiceControl(boolean voiceControl) {
        this.voiceControl = voiceControl;
    }


    /**
     * Equipment builder
     */
    public static class EquipmentBuilder{

        private Long ID;
        private boolean leather;
        private boolean airConditioning;
        private boolean parkingAssist;
        private boolean bluetooth;
        private boolean ambientLights;
        private boolean shiftPaddles;
        private boolean voiceControl;

        public EquipmentBuilder setID(Long ID){
            this.ID = ID;
            return this;
        }

        public EquipmentBuilder addLeather(boolean leather){
            this.leather = leather;
            return this;
        }

        public EquipmentBuilder addAirConditioning(boolean airConditioning){
            this.airConditioning = airConditioning;
            return this;
        }

        public EquipmentBuilder addParkingAssist(boolean parkingAssist){
            this.parkingAssist = parkingAssist;
            return this;
        }

        public EquipmentBuilder addBluetooth(boolean bluetooth){
            this.bluetooth = bluetooth;
            return this;
        }

        public EquipmentBuilder addAmbientLights(boolean ambientLights){
            this.ambientLights = ambientLights;
            return this;
        }

        public EquipmentBuilder addShiftPaddles(boolean shiftPaddles){
            this.shiftPaddles = shiftPaddles;
            return this;
        }

        public EquipmentBuilder addVoiceControl(boolean voiceControl){
            this.voiceControl = voiceControl;
            return this;
        }

        public Equipment build(){
            return new Equipment(ID,this);
        }

    }

}
