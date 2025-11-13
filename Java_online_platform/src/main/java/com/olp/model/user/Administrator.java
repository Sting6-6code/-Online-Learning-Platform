package com.olp.model.user;

import javax.persistence.*;

// line 29 "model.ump"
// line 240 "model.ump"
@Entity
@Table(name = "administrators")
public class Administrator extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //------------------------
  // CONSTRUCTOR
  //------------------------

  /**
   * Protected no-argument constructor for JPA
   */
  protected Administrator()
  {
    super();
  }

  public Administrator(String aId, String aName, String aEmail)
  {
    super(aId, aName, aEmail);
  }

  //------------------------
  // INTERFACE
  //------------------------

  /**
   * Task 1.4: 管理员类占位符
   * 未来功能：
   * - 系统管理
   * - 用户管理
   * - 课程审核
   * - 系统配置
   */

  public void delete()
  {
    super.delete();
  }

}

