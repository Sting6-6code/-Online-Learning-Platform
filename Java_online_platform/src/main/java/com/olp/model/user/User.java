package com.olp.model.user;

import javax.persistence.*;

/**
 * ===== 用户基类 =====
 * 抽象用户基类，包含所有用户的通用属性
 * Task 0.7: 添加 JPA 注解
 */
@MappedSuperclass
public abstract class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  @Id
  @Column(name = "id", length = 50)
  private String id;
  
  @Column(name = "name", length = 100, nullable = false)
  private String name;
  
  @Column(name = "email", length = 100, nullable = false, unique = true)
  private String email;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Protected no-argument constructor for JPA
   */
  protected User()
  {
    // JPA requires a no-arg constructor
  }

  public User(String aId, String aName, String aEmail)
  {
    // Task 1.1: 添加构造函数验证
    if (aId == null || aId.trim().isEmpty()) {
      throw new IllegalArgumentException("User id cannot be null or empty");
    }
    if (aName == null || aName.trim().isEmpty()) {
      throw new IllegalArgumentException("User name cannot be null or empty");
    }
    if (aEmail == null || aEmail.trim().isEmpty()) {
      throw new IllegalArgumentException("User email cannot be null or empty");
    }
    if (!aEmail.contains("@")) {
      throw new IllegalArgumentException("User email must contain @ symbol");
    }
    
    id = aId;
    name = aName;
    email = aEmail;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setId(String aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public String getId()
  {
    return id;
  }

  public String getName()
  {
    return name;
  }

  public String getEmail()
  {
    return email;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "name" + ":" + getName()+ "," +
            "email" + ":" + getEmail()+ "]";
  }
}

