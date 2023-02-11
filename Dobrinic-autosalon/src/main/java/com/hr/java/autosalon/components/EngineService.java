package com.hr.java.autosalon.components;

/**
 * Engine Service
 */
public sealed interface EngineService permits DieselEngine,PetrolEngine,HybridEngine{
    int getMaxServiceIntervalInMonths();

    default int getMaxDistanceBetweenServicesInKilometers(){
        return 100000;
    }
}
