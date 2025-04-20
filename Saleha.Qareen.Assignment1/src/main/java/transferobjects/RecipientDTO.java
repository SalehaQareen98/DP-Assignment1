/**
 * File: RecipientDTO.java
 * Author: Saleha Qareen
 * Date: 2025-02-11
 * Description: Data Transfer Object (DTO) for the Recipients entity.
 */
package transferobjects;

/**
 * The RecipientDTO class represents a recipient entity with fields matching the database table.
 */
public class RecipientDTO {

    private Integer awardID;
    private String name;
    private Integer year;
    private String city;
    private String category;

    /**
     * Gets the award ID.
     * @return award ID
     */
    public Integer getAwardID() {
        return awardID;
    }

    /**
     * Sets the award ID.
     * @param awardID the award ID to set
     */
    public void setAwardID(Integer awardID) {
        this.awardID = awardID;
    }

    /**
     * Gets the recipient name.
     * @return recipient name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the recipient name.
     * @param name the recipient name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the award year.
     * @return award year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Sets the award year.
     * @param year the award year to set
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Gets the city.
     * @return city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city.
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets the award category.
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Sets the award category.
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
