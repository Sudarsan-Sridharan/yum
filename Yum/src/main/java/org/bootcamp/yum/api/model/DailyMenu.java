/*
 * Copyright (C) 2017 JR Technologies.
 * This file is part of Yum.
 * 
 * Yum is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * 
 * Yum is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; 
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Yum. 
 * If not, see <http://www.gnu.org/licenses/>.
 */

package org.bootcamp.yum.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.joda.time.LocalDate;
/**
 * A daily Menu,  has order checked DTO
 */
@ApiModel(description = "A daily Menu,  has order checked DTO")
@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2017-05-15T17:20:26.790+03:00")

public class DailyMenu   {
  @JsonProperty("id")
  private Long id = null;

  @JsonProperty("date")
  private LocalDate date = null;

  @JsonProperty("orderId")
  private Long orderId = null;

  @JsonProperty("isFinal")
  private Boolean isFinal = null;

  @JsonProperty("foods")
  private List<FoodWithQuantity> foods = new ArrayList<FoodWithQuantity>();

  @JsonProperty("lastEdit")
  private LastEdit lastEdit = null;

  public DailyMenu id(Long id) {
    this.id = id;
    return this;
  }

   /**
   * Get id
   * @return id
  **/
  @ApiModelProperty(value = "")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public DailyMenu date(LocalDate date) {
    this.date = date;
    return this;
  }

   /**
   * Get date
   * @return date
  **/
  @ApiModelProperty(value = "")
  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public DailyMenu orderId(Long orderId) {
    this.orderId = orderId;
    return this;
  }

   /**
   * Get orderId
   * @return orderId
  **/
  @ApiModelProperty(value = "")
  public Long getOrderId() {
    return orderId;
  }

  public void setOrderId(Long orderId) {
    this.orderId = orderId;
  }

  public DailyMenu isFinal(Boolean isFinal) {
    this.isFinal = isFinal;
    return this;
  }

   /**
   * Get isFinal
   * @return isFinal
  **/
  @ApiModelProperty(value = "")
  public Boolean getIsFinal() {
    return isFinal;
  }

  public void setIsFinal(Boolean isFinal) {
    this.isFinal = isFinal;
  }

  public DailyMenu foods(List<FoodWithQuantity> foods) {
    this.foods = foods;
    return this;
  }

  public DailyMenu addFoodsItem(FoodWithQuantity foodsItem) {
    this.foods.add(foodsItem);
    return this;
  }

   /**
   * Get foods
   * @return foods
  **/
  @ApiModelProperty(value = "")
  public List<FoodWithQuantity> getFoods() {
    return foods;
  }

  public void setFoods(List<FoodWithQuantity> foods) {
    this.foods = foods;
  }

  public DailyMenu lastEdit(LastEdit lastEdit) {
    this.lastEdit = lastEdit;
    return this;
  }

   /**
   * Get lastEdit
   * @return lastEdit
  **/
  @ApiModelProperty(value = "")
  public LastEdit getLastEdit() {
    return lastEdit;
  }

  public void setLastEdit(LastEdit lastEdit) {
    this.lastEdit = lastEdit;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DailyMenu dailyMenu = (DailyMenu) o;
    return Objects.equals(this.id, dailyMenu.id) &&
        Objects.equals(this.date, dailyMenu.date) &&
        Objects.equals(this.orderId, dailyMenu.orderId) &&
        Objects.equals(this.isFinal, dailyMenu.isFinal) &&
        Objects.equals(this.foods, dailyMenu.foods) &&
        Objects.equals(this.lastEdit, dailyMenu.lastEdit);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, date, orderId, isFinal, foods, lastEdit);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DailyMenu {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    date: ").append(toIndentedString(date)).append("\n");
    sb.append("    orderId: ").append(toIndentedString(orderId)).append("\n");
    sb.append("    isFinal: ").append(toIndentedString(isFinal)).append("\n");
    sb.append("    foods: ").append(toIndentedString(foods)).append("\n");
    sb.append("    lastEdit: ").append(toIndentedString(lastEdit)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

