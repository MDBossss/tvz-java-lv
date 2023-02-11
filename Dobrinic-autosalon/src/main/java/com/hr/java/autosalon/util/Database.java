package com.hr.java.autosalon.util;

import com.hr.java.autosalon.components.*;
import com.hr.java.autosalon.enums.Condition;
import com.hr.java.autosalon.enums.FuelType;
import com.hr.java.autosalon.enums.GearboxType;
import com.hr.java.autosalon.exceptions.DatabaseException;
import com.hr.java.autosalon.main.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * Database utilities
 */
public class Database {

    private static final String DATABASE_FILE = "dat\\database.properties";
    private static final Logger logger = LoggerFactory.getLogger(Main.class);


    /**
     * Connects to H2 database.
     * @return DB connection
     * @throws SQLException SqlException
     * @throws IOException IOException
     */
    public static Connection connectToDatabase() throws SQLException, IOException{
        Properties properties = new Properties();
        properties.load(new FileReader(DATABASE_FILE));

        String JDBC_URL = properties.getProperty("JDBC_URL");
        String USERNAME = properties.getProperty("USERNAME");
        String PASSWORD = properties.getProperty("PASSWORD");

        return DriverManager.getConnection(JDBC_URL,USERNAME,PASSWORD);
    }

    /**
     * Get all VehicleHistory objects from database
     * @return Map of objects
     * @throws DatabaseException DatabaseException
     */
    public static Map<Long, VehicleHistory> getVehicleHistory() throws DatabaseException{
        Map<Long, VehicleHistory> vehicleHistoryMap = new HashMap<>();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM VEHICLE_HISTORY");

            while(rs.next()){
                Long ID = rs.getLong("ID");
                Condition condition = Condition.parseCondition(rs.getString("CONDITION"));
                Integer ownerNumber = rs.getInt("OWNER_NUMBER");
                LocalDate firstRegistrationDate = rs.getTimestamp("FIRST_REGISTRATION_DATE").toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                vehicleHistoryMap.put(ID,new VehicleHistory(ID,condition,ownerNumber,firstRegistrationDate));
            }

        }catch(SQLException | IOException e){
            logger.info("Failed to get Vehicle History from database",e);
            throw new DatabaseException();
        }
        return vehicleHistoryMap;
    }

    /**
     * Get all Gearbox objects from database
     * @return Map of objects
     * @throws DatabaseException DatabaseException
     */
    public static Map<Long, Gearbox> getGearbox() throws DatabaseException{
        Map<Long,Gearbox> gearboxMap = new HashMap<>();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM GEARBOX");

            while(rs.next()){
                Long ID = rs.getLong("ID");
                GearboxType gearboxType = GearboxType.parseGearboxType(rs.getString("GEARBOX_TYPE"));
                Integer numberOfGears = rs.getInt("NUMBER_OF_GEARS");

                gearboxMap.put(ID,new Gearbox(ID,gearboxType,numberOfGears));
            }
        }
        catch(SQLException | IOException e){
            logger.info("Failed to get Gearbox from database",e);
            throw new DatabaseException();
        }
        return gearboxMap;
    }

    /**
     * Get all Equipment objects from database
     * @return Map of objects
     * @throws DatabaseException DatabaseException
     */
    public static Map<Long, Equipment> getEquipment() throws DatabaseException{
        Map<Long,Equipment> equipmentMap = new HashMap<>();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM EQUIPMENT");

            while(rs.next()){
                Long ID = rs.getLong("ID");
                Boolean leather = rs.getBoolean("LEATHER");
                Boolean airConditioning = rs.getBoolean("AIR_CONDITIONING");
                Boolean parkingAssist = rs.getBoolean("PARKING_ASSIST");
                Boolean bluetooth = rs.getBoolean("BLUETOOTH");
                Boolean ambientLights = rs.getBoolean("AMBIENT_LIGHTS");
                Boolean shiftPaddles = rs.getBoolean("SHIFT_PADDLES");
                Boolean voiceControl = rs.getBoolean("VOICE_CONTROL");

                equipmentMap.put(ID,new Equipment.EquipmentBuilder()
                        .setID(ID)
                        .addLeather(leather)
                        .addAirConditioning(airConditioning)
                        .addParkingAssist(parkingAssist)
                        .addBluetooth(bluetooth)
                        .addAmbientLights(ambientLights)
                        .addShiftPaddles(shiftPaddles)
                        .addVoiceControl(voiceControl)
                        .build()
                );
            }

        }catch(SQLException | IOException e){
            logger.info("Failed to get Equipment from database",e);
            throw new DatabaseException();
        }
        return equipmentMap;
    }

    /**
     * Get all Engine objects from database
     * @return Map of objects
     * @throws DatabaseException DatabaseException
     */
    public static Map<Long, Engine> getEngine() throws DatabaseException{
        Map<Long,Engine> engineMap = new HashMap<>();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM ENGINE");

            while(rs.next()){
                Long ID = rs.getLong("ID");
                Integer mileage = rs.getInt("MILEAGE");
                Double fuelConsumption = rs.getDouble("FUEL_CONSUMPTION");
                Double horsepower = rs.getDouble("HORSEPOWER");
                FuelType fuelType = FuelType.parseFuelType(rs.getString("FUEL_TYPE"));

                Engine engine;
                switch(fuelType){
                    case DIESEL -> engine = new DieselEngine(ID,mileage,fuelConsumption,horsepower);
                    case PETROL -> engine = new PetrolEngine(ID,mileage,fuelConsumption,horsepower);
                    case HYBRID -> engine = new HybridEngine(ID,mileage,fuelConsumption,horsepower);
                    default -> {
                        logger.info("Invalid engine type");
                        throw new IllegalArgumentException("Invalid engine type !");
                    }
                }
                engineMap.put(ID,engine);
            }
        }catch(SQLException | IOException e){
            logger.info("Failed to get Engine from database");
            throw new DatabaseException();
        }
        return engineMap;
    }

    /**
     * Get all car objects from database
     * @return map of objects
     * @throws DatabaseException database exception
     */
    public static Map<Long,Car> getCar() throws DatabaseException{
        Map<Long,Car> carMap = new HashMap<>();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM CAR");

            while(rs.next()){
                Long ID = rs.getLong("ID");
                String manufacturer = rs.getString("MANUFACTURER");
                String type = rs.getString("TYPE");
                String color = rs.getString("COLOR");
                VehicleHistory vehicleHistory = getVehicleHistory().get(rs.getLong("VEHICLE_HISTORY_ID"));
                Engine engine = getEngine().get(rs.getLong("ENGINE_ID"));
                Integer doorCount = rs.getInt("DOOR_COUNT");
                Equipment equipment = getEquipment().get(rs.getLong("EQUIPMENT_ID"));
                Gearbox gearbox = getGearbox().get(rs.getLong("GEARBOX_ID"));

                carMap.put(ID,new Car(ID,manufacturer,type,color,vehicleHistory,engine,doorCount,equipment,gearbox));
            }
        }catch(SQLException | IOException e){
            logger.info("Failed to get Car from database");
            throw new DatabaseException();
        }
        return carMap;
    }

    /**
     * Adds car along with all other objects necessary to the database
     * @param car car to add
     * @throws DatabaseException database exception
     */
    public static void addCar(Car car) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatementGearbox = connection.prepareStatement("INSERT INTO GEARBOX(GEARBOX_TYPE, NUMBER_OF_GEARS) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatementGearbox.setString(1,car.getGearbox().getGearboxType().value);
            preparedStatementGearbox.setInt(2,car.getGearbox().getNumberOfGears());
            preparedStatementGearbox.executeUpdate();
            ResultSet rs1 = preparedStatementGearbox.getGeneratedKeys();
            Long GEARBOX_ID = -1L;
            if(rs1.next()){
                GEARBOX_ID = rs1.getLong(1);
            }

            PreparedStatement preparedStatementEquipment = connection.prepareStatement("INSERT INTO EQUIPMENT(LEATHER,AIR_CONDITIONING,PARKING_ASSIST,BLUETOOTH,AMBIENT_LIGHTS,SHIFT_PADDLES,VOICE_CONTROL) VALUES (?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatementEquipment.setBoolean(1,car.getEquipment().isLeather());
            preparedStatementEquipment.setBoolean(2,car.getEquipment().isAirConditioning());
            preparedStatementEquipment.setBoolean(3,car.getEquipment().isParkingAssist());
            preparedStatementEquipment.setBoolean(4,car.getEquipment().isBluetooth());
            preparedStatementEquipment.setBoolean(5,car.getEquipment().isAmbientLights());
            preparedStatementEquipment.setBoolean(6,car.getEquipment().isShiftPaddles());
            preparedStatementEquipment.setBoolean(7,car.getEquipment().isVoiceControl());
            preparedStatementEquipment.executeUpdate();
            ResultSet rs2 = preparedStatementEquipment.getGeneratedKeys();
            Long EQUIPMENT_ID = -1L;
            if(rs2.next()){
                EQUIPMENT_ID = rs2.getLong(1);
            }

            PreparedStatement preparedStatementVehicleHistory = connection.prepareStatement("INSERT INTO VEHICLE_HISTORY(CONDITION,OWNER_NUMBER,FIRST_REGISTRATION_DATE) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatementVehicleHistory.setString(1,car.getVehicleHistory().condition().toString());
            preparedStatementVehicleHistory.setInt(2,car.getVehicleHistory().ownerNumber());
            preparedStatementVehicleHistory.setDate(3,Date.valueOf(car.getVehicleHistory().firstRegistrationDate()));
            preparedStatementVehicleHistory.executeUpdate();
            ResultSet rs3 = preparedStatementVehicleHistory.getGeneratedKeys();
            Long VEHICLE_HISTORY_ID = -1L;
            if(rs3.next()){
                VEHICLE_HISTORY_ID = rs3.getLong(1);
            }

            PreparedStatement preparedStatementEngine = connection.prepareStatement("INSERT INTO ENGINE(MILEAGE,FUEL_CONSUMPTION,HORSEPOWER,FUEL_TYPE) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            if(car.getEngine() instanceof DieselEngine){
                preparedStatementEngine.setInt(1,car.getEngine().getMileage());
                preparedStatementEngine.setDouble(2,((DieselEngine) car.getEngine()).getFuelConsumption());
                preparedStatementEngine.setDouble(3,((DieselEngine) car.getEngine()).getHorsepower());
                preparedStatementEngine.setString(4,((DieselEngine) car.getEngine()).getFuelType().value);
            }
            else if(car.getEngine() instanceof PetrolEngine){
                preparedStatementEngine.setInt(1,car.getEngine().getMileage());
                preparedStatementEngine.setDouble(2,((PetrolEngine) car.getEngine()).getFuelConsumption());
                preparedStatementEngine.setDouble(3,((PetrolEngine) car.getEngine()).getHorsepower());
                preparedStatementEngine.setString(4,((PetrolEngine) car.getEngine()).getFuelType().value);
            }
            else if(car.getEngine() instanceof HybridEngine){
                preparedStatementEngine.setInt(1,car.getEngine().getMileage());
                preparedStatementEngine.setDouble(2,((HybridEngine) car.getEngine()).getFuelConsumption());
                preparedStatementEngine.setDouble(3,((HybridEngine) car.getEngine()).getHorsepower());
                preparedStatementEngine.setString(4,((HybridEngine) car.getEngine()).getFuelType().value);
            }
            preparedStatementEngine.executeUpdate();
            ResultSet rs4 = preparedStatementEngine.getGeneratedKeys();
            Long ENGINE_ID = -1L;
            if(rs4.next()){
                ENGINE_ID = rs4.getLong(1);
            }

            PreparedStatement preparedStatementCar = connection.prepareStatement("INSERT INTO CAR(MANUFACTURER,TYPE,COLOR,VEHICLE_HISTORY_ID,ENGINE_ID,DOOR_COUNT,EQUIPMENT_ID,GEARBOX_ID) VALUES (?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatementCar.setString(1,car.getManufacturer());
            preparedStatementCar.setString(2,car.getType());
            preparedStatementCar.setString(3,car.getColor());
            preparedStatementCar.setLong(4,VEHICLE_HISTORY_ID);
            preparedStatementCar.setLong(5,ENGINE_ID);
            preparedStatementCar.setInt(6,car.getDoorCount());
            preparedStatementCar.setLong(7,EQUIPMENT_ID);
            preparedStatementCar.setLong(8,GEARBOX_ID);
            preparedStatementCar.executeUpdate();

        }catch(SQLException | IOException e){
            throw new DatabaseException();
        }
    }

    /**
     * Adds user car along with all other objects necessary to the database
     * @param car car to add
     * @throws DatabaseException dbe
     */
    public static void addUserCar(Car car) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatementGearbox = connection.prepareStatement("INSERT INTO GEARBOX(GEARBOX_TYPE, NUMBER_OF_GEARS) VALUES (?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatementGearbox.setString(1,car.getGearbox().getGearboxType().value);
            preparedStatementGearbox.setInt(2,car.getGearbox().getNumberOfGears());
            preparedStatementGearbox.executeUpdate();
            ResultSet rs1 = preparedStatementGearbox.getGeneratedKeys();
            Long GEARBOX_ID = -1L;
            if(rs1.next()){
                GEARBOX_ID = rs1.getLong(1);
            }

            PreparedStatement preparedStatementEquipment = connection.prepareStatement("INSERT INTO EQUIPMENT(LEATHER,AIR_CONDITIONING,PARKING_ASSIST,BLUETOOTH,AMBIENT_LIGHTS,SHIFT_PADDLES,VOICE_CONTROL) VALUES (?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatementEquipment.setBoolean(1,car.getEquipment().isLeather());
            preparedStatementEquipment.setBoolean(2,car.getEquipment().isAirConditioning());
            preparedStatementEquipment.setBoolean(3,car.getEquipment().isParkingAssist());
            preparedStatementEquipment.setBoolean(4,car.getEquipment().isBluetooth());
            preparedStatementEquipment.setBoolean(5,car.getEquipment().isAmbientLights());
            preparedStatementEquipment.setBoolean(6,car.getEquipment().isShiftPaddles());
            preparedStatementEquipment.setBoolean(7,car.getEquipment().isVoiceControl());
            preparedStatementEquipment.executeUpdate();
            ResultSet rs2 = preparedStatementEquipment.getGeneratedKeys();
            Long EQUIPMENT_ID = -1L;
            if(rs2.next()){
                EQUIPMENT_ID = rs2.getLong(1);
            }

            PreparedStatement preparedStatementVehicleHistory = connection.prepareStatement("INSERT INTO VEHICLE_HISTORY(CONDITION,OWNER_NUMBER,FIRST_REGISTRATION_DATE) VALUES (?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatementVehicleHistory.setString(1,car.getVehicleHistory().condition().toString());
            preparedStatementVehicleHistory.setInt(2,car.getVehicleHistory().ownerNumber());
            preparedStatementVehicleHistory.setDate(3,Date.valueOf(car.getVehicleHistory().firstRegistrationDate()));
            preparedStatementVehicleHistory.executeUpdate();
            ResultSet rs3 = preparedStatementVehicleHistory.getGeneratedKeys();
            Long VEHICLE_HISTORY_ID = -1L;
            if(rs3.next()){
                VEHICLE_HISTORY_ID = rs3.getLong(1);
            }

            PreparedStatement preparedStatementEngine = connection.prepareStatement("INSERT INTO ENGINE(MILEAGE,FUEL_CONSUMPTION,HORSEPOWER,FUEL_TYPE) VALUES (?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            if(car.getEngine() instanceof DieselEngine){
                preparedStatementEngine.setInt(1,car.getEngine().getMileage());
                preparedStatementEngine.setDouble(2,((DieselEngine) car.getEngine()).getFuelConsumption());
                preparedStatementEngine.setDouble(3,((DieselEngine) car.getEngine()).getHorsepower());
                preparedStatementEngine.setString(4,((DieselEngine) car.getEngine()).getFuelType().value);
            }
            else if(car.getEngine() instanceof PetrolEngine){
                preparedStatementEngine.setInt(1,car.getEngine().getMileage());
                preparedStatementEngine.setDouble(2,((PetrolEngine) car.getEngine()).getFuelConsumption());
                preparedStatementEngine.setDouble(3,((PetrolEngine) car.getEngine()).getHorsepower());
                preparedStatementEngine.setString(4,((PetrolEngine) car.getEngine()).getFuelType().value);
            }
            else if(car.getEngine() instanceof HybridEngine){
                preparedStatementEngine.setInt(1,car.getEngine().getMileage());
                preparedStatementEngine.setDouble(2,((HybridEngine) car.getEngine()).getFuelConsumption());
                preparedStatementEngine.setDouble(3,((HybridEngine) car.getEngine()).getHorsepower());
                preparedStatementEngine.setString(4,((HybridEngine) car.getEngine()).getFuelType().value);
            }
            preparedStatementEngine.executeUpdate();
            ResultSet rs4 = preparedStatementEngine.getGeneratedKeys();
            Long ENGINE_ID = -1L;
            if(rs4.next()){
                ENGINE_ID = rs4.getLong(1);
            }

            PreparedStatement preparedStatementCar = connection.prepareStatement("INSERT INTO USER_CAR(MANUFACTURER,TYPE,COLOR,VEHICLE_HISTORY_ID,ENGINE_ID,DOOR_COUNT,EQUIPMENT_ID,GEARBOX_ID,USERNAME,TIMESTAMP) VALUES (?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatementCar.setString(1,car.getManufacturer());
            preparedStatementCar.setString(2,car.getType());
            preparedStatementCar.setString(3,car.getColor());
            preparedStatementCar.setLong(4,VEHICLE_HISTORY_ID);
            preparedStatementCar.setLong(5,ENGINE_ID);
            preparedStatementCar.setInt(6,car.getDoorCount());
            preparedStatementCar.setLong(7,EQUIPMENT_ID);
            preparedStatementCar.setLong(8,GEARBOX_ID);
            Thread thread1 = new Thread(() -> {
                try{
                    preparedStatementCar.setString(9,LoginUtils.getCurrentUser());

                }catch (SQLException e){
                    logger.info(e.getMessage(),e);
                }
            });
            thread1.start();
            try{
                thread1.join();
            }catch (InterruptedException e){
                logger.info(e.getMessage(),e);
            }
            preparedStatementCar.setTimestamp(10, Timestamp.valueOf(LocalDateTime.now()));
            preparedStatementCar.executeUpdate();

        }catch(SQLException | IOException e){
            throw new DatabaseException();
        }
    }

    /**
     * Get all user car objects from database
     * @return map of user cars
     * @throws DatabaseException dbe
     */
    public static Map<Long,Car> getUserCar() throws DatabaseException{
        Map<Long,Car> carMap = new HashMap<>();

        try(Connection connection = connectToDatabase()){
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM USER_CAR");


            while(rs.next()){
                Long ID = rs.getLong("ID");
                String manufacturer = rs.getString("MANUFACTURER");
                String type = rs.getString("TYPE");
                String color = rs.getString("COLOR");
                VehicleHistory vehicleHistory = getVehicleHistory().get(rs.getLong("VEHICLE_HISTORY_ID"));
                Engine engine = getEngine().get(rs.getLong("ENGINE_ID"));
                Integer doorCount = rs.getInt("DOOR_COUNT");
                Equipment equipment = getEquipment().get(rs.getLong("EQUIPMENT_ID"));
                Gearbox gearbox = getGearbox().get(rs.getLong("GEARBOX_ID"));
                String username = rs.getString("USERNAME");

                Thread thread1 = new Thread(() -> {
                    if(Objects.equals(username, LoginUtils.getCurrentUser())){
                        carMap.put(ID,new Car(ID,manufacturer,type,color,vehicleHistory,engine,doorCount,equipment,gearbox));
                    }
                });
                thread1.start();
                try{
                    thread1.join();
                }catch (InterruptedException e){
                    logger.info(e.getMessage(),e);
                }

            }
        }catch(SQLException | IOException e){
            logger.info("Failed to get Car from database");
            throw new DatabaseException();
        }
        return carMap;
    }

    /**
     * Deletes engine from database with ID
     * @param ID engine id
     * @throws DatabaseException  database exception
     */
    public static void deleteEngine(Long ID) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM ENGINE WHERE ID = ?");
            preparedStatement.setLong(1,ID);
            preparedStatement.executeUpdate();

        }catch (SQLException | IOException e){
            logger.info("Failed to delete ENGINE from database");
            throw new DatabaseException();
        }
    }

    /**
     * Deletes vehicle history from database with ID
     * @param ID id
     * @throws DatabaseException dbe
     */
    public static void deleteVehicleHistory(Long ID) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM VEHICLE_HISTORY WHERE ID = ?");
            preparedStatement.setLong(1,ID);
            preparedStatement.executeUpdate();

        }catch (SQLException | IOException e){
            logger.info("Failed to delete VEHICLE_HISTORY from database");
            throw new DatabaseException();
        }
    }

    /**
     * Deletes equipment from database with ID
     * @param ID id
     * @throws DatabaseException dbe
     */
    public static void deleteEquipment(Long ID) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM EQUIPMENT WHERE ID = ?");
            preparedStatement.setLong(1,ID);
            preparedStatement.executeUpdate();

        }catch (SQLException | IOException e){
            logger.info("Failed to delete EQUIPMENT from database");
            throw new DatabaseException();
        }
    }

    /**
     * Deletes gearbox from database with ID
     * @param ID id
     * @throws DatabaseException DBE
     */
    public static void deleteGearbox(Long ID) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM GEARBOX WHERE ID = ?");
            preparedStatement.setLong(1,ID);
            preparedStatement.executeUpdate();

        }catch (SQLException | IOException e){
            logger.info("Failed to delete GEARBOX from database");
            throw new DatabaseException();
        }
    }

    /**
     * Deletes car from database with ID
     * @param car car
     * @throws DatabaseException dbe
     */
    public static void deleteCar(Car car) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM USER_CAR WHERE ID = ?");
            preparedStatement.setLong(1,car.getID());
            preparedStatement.executeUpdate();

            deleteVehicleHistory(car.getVehicleHistory().ID());
            deleteEngine(car.getEngine().getID());
            deleteEquipment(car.getEquipment().getID());
            deleteGearbox(car.getGearbox().getID());


        }catch (SQLException | IOException e){
            logger.info("Failed to delete CAR from database");
            throw new DatabaseException();
        }
    }

    /**
     * Updates selected engine
     * @param engine engine to update
     * @throws DatabaseException dbe
     */
    public static void updateEngine(Engine engine) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE ENGINE SET MILEAGE = ?, FUEL_CONSUMPTION = ?, HORSEPOWER = ?, FUEL_TYPE = ? WHERE ID = ?");
            if(engine instanceof DieselEngine){
                preparedStatement.setInt(1,engine.getMileage());
                preparedStatement.setDouble(2,((DieselEngine) engine).getFuelConsumption());
                preparedStatement.setDouble(3,((DieselEngine) engine).getHorsepower());
                preparedStatement.setString(4,((DieselEngine) engine).getFuelType().value);
                preparedStatement.setLong(5,engine.getID());
            }
            else if(engine instanceof PetrolEngine){
                preparedStatement.setInt(1,engine.getMileage());
                preparedStatement.setDouble(2,((PetrolEngine) engine).getFuelConsumption());
                preparedStatement.setDouble(3,((PetrolEngine) engine).getHorsepower());
                preparedStatement.setString(4,((PetrolEngine) engine).getFuelType().value);
                preparedStatement.setLong(5,engine.getID());
            }
            else if(engine instanceof HybridEngine){
                preparedStatement.setInt(1,engine.getMileage());
                preparedStatement.setDouble(2,((HybridEngine) engine).getFuelConsumption());
                preparedStatement.setDouble(3,((HybridEngine) engine).getHorsepower());
                preparedStatement.setString(4,((HybridEngine) engine).getFuelType().value);
                preparedStatement.setLong(5,engine.getID());
            }
            preparedStatement.executeUpdate();

        }catch (SQLException | IOException e){
            logger.info("Failed to delete CAR from database");
            throw new DatabaseException();
        }
    }

    /**
     * Updates selected vehicle history
     * @param vehicleHistory vehicle history
     * @throws DatabaseException dbe
     */
    public static void updateVehicleHistory(VehicleHistory vehicleHistory) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE VEHICLE_HISTORY SET CONDITION = ?, OWNER_NUMBER = ?, FIRST_REGISTRATION_DATE = ? WHERE ID = ?");
            preparedStatement.setString(1,vehicleHistory.condition().value);
            preparedStatement.setInt(2,vehicleHistory.ownerNumber());
            preparedStatement.setDate(3,Date.valueOf(vehicleHistory.firstRegistrationDate()));
            preparedStatement.setLong(4,vehicleHistory.ID());
            preparedStatement.executeUpdate();

        }catch (SQLException | IOException e){
            logger.info("Failed to delete CAR from database");
            throw new DatabaseException();
        }
    }

    /**
     * Updates selected equipment
     * @param equipment equipment
     * @throws DatabaseException dbe
     */
    public static void updateEquipment(Equipment equipment) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE EQUIPMENT SET LEATHER = ?, AIR_CONDITIONING = ?, PARKING_ASSIST = ?, BLUETOOTH = ?, AMBIENT_LIGHTS = ?, SHIFT_PADDLES = ?, VOICE_CONTROL = ? WHERE ID = ?");
            preparedStatement.setBoolean(1, equipment.isLeather());
            preparedStatement.setBoolean(2, equipment.isAirConditioning());
            preparedStatement.setBoolean(3, equipment.isParkingAssist());
            preparedStatement.setBoolean(4,equipment.isBluetooth());
            preparedStatement.setBoolean(5, equipment.isAmbientLights());
            preparedStatement.setBoolean(6, equipment.isShiftPaddles());
            preparedStatement.setBoolean(7, equipment.isVoiceControl());
            preparedStatement.setLong(8, equipment.getID());
            preparedStatement.executeUpdate();

        }catch (SQLException | IOException e){
            logger.info("Failed to delete CAR from database");
            throw new DatabaseException();
        }
    }

    /**
     * Updates selected gearbox
     * @param gearbox gearbox
     * @throws DatabaseException dbe
     */
    public static void updateGearbox(Gearbox gearbox) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE GEARBOX SET GEARBOX_TYPE = ?, NUMBER_OF_GEARS = ? WHERE ID = ?");
            preparedStatement.setString(1,gearbox.getGearboxType().value);
            preparedStatement.setInt(2,gearbox.getNumberOfGears());
            preparedStatement.setLong(3,gearbox.getID());
            preparedStatement.executeUpdate();

        }catch (SQLException | IOException e){
            logger.info("Failed to delete CAR from database");
            throw new DatabaseException();
        }
    }

    /**
     * Updates selected car
     * @param car car
     * @throws DatabaseException dbe
     */
    public static void updateCar(Car car) throws DatabaseException{
        try(Connection connection = connectToDatabase()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE USER_CAR SET MANUFACTURER = ?, TYPE = ?, COLOR = ?, DOOR_COUNT = ? WHERE ID = ?");
            preparedStatement.setString(1,car.getManufacturer());
            preparedStatement.setString(2,car.getType());
            preparedStatement.setString(3,car.getColor());
            preparedStatement.setInt(4,car.getDoorCount());
            preparedStatement.setLong(5,car.getID());

            updateEngine(car.getEngine());
            updateEquipment(car.getEquipment());
            updateVehicleHistory(car.getVehicleHistory());
            updateGearbox(car.getGearbox());

            preparedStatement.executeUpdate();

        }catch (SQLException | IOException e){
            logger.info("Failed to delete CAR from database");
            throw new DatabaseException();
        }
    }


}
