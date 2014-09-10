package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.{Date}


case class CoffeeCup(
    name: String, 
    roaster: Option[String],
    roastDate: Date,
    producer: Option[String], 
    brewDate: Date,
    price: Int,
    rating: Int)

object CoffeeCup {
  
  def all(): List[CoffeeCup] = Nil
  
  def create(name: String, 
             roaster: String,
             roastDate: Date,
             producer: String,
             brewDate: Date,
             price: Int,
             rating: Int) {
    
    val c = CoffeeCup(name, 
                      Option(roaster),
                      roastDate,
                      Option(producer), 
                      brewDate,
                      price,
                      rating)
  }
  
  def delete(id: Long) {}

  /**
   * Parse a Computer from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("coffee.id") ~
    get[String]("coffee.name") ~
    get[Option[String]]("coffee.roaster") ~
    get[Date]("coffee.roast_date") ~
    get[Option[String]]("coffee.producer") ~
    get[Date]("coffee.brew_date") ~
    get[Int]("coffee.price") ~
    get[Int]("coffee.rating") map {
      case id~name~roaster~roast_date~producer~brew_date~price~rating => CoffeeCup(name, roaster, roast_date, producer, brew_date, price, rating)
    }
  }


  // /**
  //  * Return a page of (Computer,Company).
  //  *
  //  * @param page Page to display
  //  * @param pageSize Number of computers per page
  //  * @param orderBy Computer property used for sorting
  //  * @param filter Filter applied on the name column
  //  */
  // def list(page: Int = 0, pageSize: Int = 10, orderBy: Int = 1, filter: String = "%"): Page[(Computer, Option[Company])] = {
    
  //   val offest = pageSize * page
    
  //   DB.withConnection { implicit connection =>
      
  //     val coffees = SQL(
  //       """
  //         select * from coffee 
  //       """
  //     ).on(
  //     )

  //     // val totalRows = SQL(
  //     //   """
  //     //     select count(*) from computer 
  //     //     left join company on computer.company_id = company.id
  //     //     where computer.name like {filter}
  //     //   """
  //     // ).on(
  //     //   'filter -> filter
  //     // ).as(scalar[Long].single)

  //     Page(computers, page, offest, totalRows)
      
  //   }
    
  // }

    /**
   * Retrieve a computer from the id.
   */
  def findById(name: String): Option[CoffeeCup] = {
    DB.withConnection { implicit connection =>
      SQL("select * from coffee where name = {name} limit 1").on('name -> name).as(CoffeeCup.simple.singleOpt)
    }
  }


  /**
   * Insert a new cup of coffee.
   *
   * @param coffee The computer values.
   */
  def insert(coffee: CoffeeCup) = {
    println("INserting")
    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into coffee values (
            (select next value for coffee_seq),
            {name}, {producer}, {brew_date}, {rating}
          )
        """
      ).on(
        'name -> coffee.name,
        'producer -> coffee.producer,
        'brew_date -> coffee.brewDate,
        'rating -> coffee.rating
      ).executeUpdate()
    }
  }
  
}