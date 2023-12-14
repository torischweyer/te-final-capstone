# Backend code snippets

## Database

Modifying the `schema.sql` script to create tables (sample):

```
BEGIN TRANSACTION;

DROP TABLE IF EXISTS users, <-- additional table names here -->;
<-- Note: unless you're using CASCADE, be sure to delete dependent tables first -->

CREATE TABLE table_name (
	row_id SERIAL,
	name varchar(50) NOT NULL UNIQUE,
	type varchar(20) NOT NULL,
	favorite boolean NOT NULL,
	measurement decimal(5,2) NOT NULL,
	join_table_id int,
	
	CONSTRAINT pk_row_id PRIMARY KEY (row_id),
	CONSTRAINT fk_join_table_id foreign key (join_table_id) references join_table (join_table_id)
);

COMMIT TRANSACTION;

```
For creating a sequence: 
```dtd
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;
```
Useful SQL data types:

| Data type               | Description                                                                                                        |
|-------------------------|--------------------------------------------------------------------------------------------------------------------|
| `char(#)`               | Character.  # defines the length of the data. Note: unused characters at the end are then considered blank spaces. |
| `varchar(#)`            | Varying character. # defines the length of the data. Note: unused characters at the end are fine.                  |
| `int`                   | integer, like Java's int                                                                                           |
| `decimal`               | floating point numbers, similar to Java's double or BigDecimal.                                                    |
| `boolean`               | Note: not the same in all SQL databases.                                                                           |
| `DATE`                  | stored as YYYY-MM-DD; recognizes input as MM/DD/YYYY                                                               |
| `TIME`                  | hh:mm:ss                                                                                                           |
| `TIMESTAMP or DATETIME` | YYYY-MM-DD hh:mm:ss; recognizes MM/DD/YYYY hh:mm:ss                                                                |

### Populating the table

Modifying the `data.sql` script to insert data into tables (sample):
```dtd
BEGIN TRANSACTION;

INSERT INTO recipes (recipe_name, favorite, category, user_id) VALUES ('recipe1', false, 'category1', 1);
INSERT INTO recipes (recipe_name, favorite, category, user_id) VALUES ('recipe2', false, 'category2', 2);
INSERT INTO recipes (recipe_name, favorite, category, user_id) VALUES ('recipe3', false, 'category3', 3);

-- Link tables --
INSERT INTO recipe_user (recipe_id, user_id) VALUES (1, 1);
INSERT INTO recipe_user (recipe_id, user_id) VALUES (2, 2);
INSERT INTO recipe_user (recipe_id, user_id) VALUES (3, 3);

-- Update data --
UPDATE table_name SET column1 = value1, column2 = value2 WHERE column = value;

-- Delete data -- 
DELETE FROM table_name WHERE column = value;

COMMIT TRANSACTION;
```

### Constraints

| Constraint    | Properties that the column data must comply with                                                                                                                                   |
|---------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| `NOT NULL`    | The column cannot contain a null value                                                                                                                                             |
| `UNIQUE`      | The column can only contain unique values                                                                                                                                          |
| `PRIMARY KEY` | Enforces NOT NULL and UNIQUE.  Allows Foreign Key relationships to be established.                                                                                                 |
| `FOREIGN KEY` | Only allows values where the value exists on the related table.  Does not allow the related value to be removed from the related table as long as the foreign key value is in use. |
| `CHECK`       | Specifies a list of acceptable values that can be added into a column                                                                                                              |
| `DEFAULT`     | Provides a default value for a column, if no value is provided.                                                                                                                    |


## Spring Boot
Note: Spring Boot has been configured to run on port `9000` for this project. You might be used to port `8080` from earlier in the cohort, but it's changed so as not to conflict with the Vue server that you'll be running concurrently.

### Datasource

A Datasource has been configured for you in `/src/resources/application.properties`. It connects to the database using the `capstone_appuser` database user. You can change the name of this database if you want, but remember to change it here and in the `create.sh` script in the database folder:

```
# datasource connection properties
spring.datasource.url=jdbc:postgresql://localhost:5432/final_capstone
spring.datasource.name=final_capstone
spring.datasource.username=final_capstone_appuser
spring.datasource.password=finalcapstone
```

### JdbcTemplate

If you look in `/src/main/java/com/techelevator/dao`, you'll see `JdbcUserDao`. This is an example of how to get an instance of `JdbcTemplate` in your DAOs. If you declare a field of type `JdbcTemplate` and add it as an argument to the constructor, Spring automatically injects an instance for you:

```java
@Service
public class JdbcUserDao implements UserDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
```

### CORS

Any controller that'll be accessed from a client like the Vue Starter application needs the `@CrossOrigin` annotation. This
tells the browser that you're allowing the client application to access this resource:

```java
@RestController
@CrossOrigin
public class AuthenticationController {
    // ...
}
```

## Main

Most of the functionality related to Security is located in the `/src/main/java/com/techelevator/security` package. You shouldn't have to modify anything here, but feel free to go through the code if you want to see how things work.

### DAOs / JdbcDaos

DAOs: interface, just method signatures.

JDBC DAO samples below:

```dtd
@Component
public class JdbcClassDao implements ClassDao {
    private final JdbcTemplate jdbcTemplate;

    public JdbcClassDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

//GET and return a list
    @Override
    public List<Class> fetchAllClasses() {

        List<Class> classList = new ArrayList<>();
        String sql = "SELECT <list columns here> FROM table_name";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                Task task = mapRowToClass(results);
                classList.add(class);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return classList;
    }

//GET and return an object
    @Override
    public Class fetchClassById(int classId) {

        Class class = null;
        String sql = "SELECT <list columns here> " +
                "FROM table_name" +
                "WHERE class_id = ?";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, classId);

            if  (results.next()) {
                task = mapRowToClass(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return class;
    }

//GET and return an object, with wildcard

    @Override
    public List<Customer> getCustomersByName(String search, boolean useWildCard) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT customer_id, first_name, last_name, street_address, city, phone_number, " +
                "email_address, email_offers " +
                "FROM customer " +
                "WHERE first_name LIKE ? OR last_name LIKE ?;";

        if (useWildCard) {
            search = "%" + search + "%";
        }

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, search, search);
        while (results.next()) {
            customers.add(mapRowToCustomer(results));
        }
        return customers;
    }

//CREATE or ADD a new object
    @Override
    public Class addClass(Class newClass) {

        String sql = "INSERT INTO table_name (<columns here>)" +
                "VALUES (?, ?, ?, ?, ?) RETURNING class_id";
        int new_id = jdbcTemplate.queryForObject(sql, int.class, newClass.getClass(), <insert other getters here>);
        newClass.setClassId(new_id);

        return newClass;
    }

//UPDATE an object
    @Override
    public Class updateClass(Class updatedClass) {

        String sql = "UPDATE table_name SET col1 = ?, col2 = ?, col3 = ? " +
                "WHERE class_id = ?";
                
        jdbcTemplate.update(sql, updatedTask.getClass(), <other getters here>);

        if (rowCount < 1) {
            throw new DaoException("Error. Class was not updated.");
        }
        return updatedClass;
    }

//DELETE an object
    @Override
    public int deleteClassById(int classId) {
        try {
            String sql = "DELETE FROM table_name WHERE class_id = ?";
            return jdbcTemplate.update(sql, classId);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Error deleting class " + classId, e);
        }
    }

    private Object mapRowToObject(SqlRowSet rowSet) {
        Object object = new Object();
        object.setObjectId(rowSet.getInt("object_id"));
        object.setName(rowSet.getString("name"));
        object.setFavorited(rowSet.getBoolean("favorited"));
      
        return object;
    }
}

```
### Consuming RESTful APIs: services

```dtd
public class LocationService {

    private static final String API_BASE_URL = "http://localhost:3000/locations";
    private final RestTemplate restTemplate = new RestTemplate();

    public Location[] getAll() {
        Location[] locations = null;
        try {
            locations = restTemplate.getForObject(API_BASE_URL, Location[].class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return locations;
    }

    public Location getOne(int id) {
        Location location = null;
        try {
            location = restTemplate.getForObject(API_BASE_URL + "/" + id, Location.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return location;
    }

    public Location add(Location newLocation) {
        HttpEntity<Location> entity = makeEntity(newLocation);

        Location returnedLocation = null;
        try {
            returnedLocation = restTemplate.postForObject(API_BASE_URL, entity, Location.class);
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return returnedLocation;
    }

    public boolean update(Location updatedLocation) {
        HttpEntity<Location> entity = makeEntity(updatedLocation);

        boolean success = false;
        try {
            restTemplate.put(API_BASE_URL + "/" + updatedLocation.getId(), entity);
            success = true;
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    public boolean delete(int id) {
        boolean success = false;
        try {
            restTemplate.delete(API_BASE_URL + "/" + id);
            success = true;
        } catch (RestClientResponseException e) {
            BasicLogger.log(e.getRawStatusCode() + " : " + e.getStatusText());
        } catch (ResourceAccessException e) {
            BasicLogger.log(e.getMessage());
        }
        return success;
    }

    private HttpEntity<Location> makeEntity(Location location){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(location, headers);
    }
}

```

### Controller methods: server-side APIs

```dtd
@RestController
@RequestMapping("/locations")
public class LocationController {

    private LocationDao locationDao;

    public LocationController(LocationDao locationDao) {
        this.locationDao = locationDao;
    }

//GET
    @RequestMapping(path = "", method = RequestMethod.GET)
    public List<Location> list() {
        return locationDao.getLocations();
    }

//GET by ID
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public Location get(@PathVariable int id) {
        Location location = locationDao.getLocationById(id);
        if (location == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        } else {
            return location;
        }
    }
    
//CREATE or ADD
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(path = "", method = RequestMethod.POST)
    public Location add(@Valid @RequestBody Location location) {
        return locationDao.createLocation(location);
    }

//UPDATE
    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public Location update(@Valid @RequestBody Location location, @PathVariable int id) {
        // The id on the path takes precedence over the id in the request body, if any
        location.setId(id);
        try {
            Location updatedLocation = locationDao.updateLocation(location);
            return updatedLocation;
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Location not found");
        }
    }

//DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    public void delete(@PathVariable int id) {
        locationDao.deleteLocationById(id);
    }
}
```
### DAO exception

```dtd
public class DaoException extends RuntimeException {
    public DaoException() {
        super();
    }

    public DaoException(String message) {
        super(message);
    }

    public DaoException(String message, Exception cause) {
        super(message, cause);
    }
}
```
## Additional snippets

### Add here

`sample thing that highlights` with explanatory text. 