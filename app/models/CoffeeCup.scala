package models

import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._

import java.util.{Date}

import play.api.libs.json.Json


case class CoffeeCup(
    name: String, 
    roaster: Option[String],
    roastDate: Date,
    producer: Option[String], 
    brewDate: Date,
    price: Int,
    rating: Int,
    flavors: Map[String, Int])

object CoffeeCup {
  
  def all(): List[CoffeeCup] = Nil
  
  def create(name: String, 
             roaster: String,
             roastDate: Date,
             producer: String,
             brewDate: Date,
             price: Int,
             rating: Int,
             flavors: Map[String, Int]) {
    

    val c = CoffeeCup(name, 
                      Option(roaster),
                      roastDate,
                      Option(producer), 
                      brewDate,
                      price,
                      rating,
                      flavors)
  }
  
  def delete(id: Long) {}

  /**
   * Parse a Computer from a ResultSet
   */
  val simple = {
    get[Pk[Long]]("coffee.id") ~
    get[String]("coffee.name") ~
    get[Option[String]]("coffee.roaster") ~
    get[Date]("coffee.roastDate") ~
    get[Option[String]]("coffee.producer") ~
    get[Date]("coffee.brewDate") ~
    get[Int]("coffee.price") ~
    get[Int]("coffee.rating") ~
    get[String]("coffee.flavors") map {
      case id~name~roaster~roastDate~producer~brewDate~price~rating~flavors => {
        val flavorsJson = Json.parse(flavors)

        // TODO: collect errors here
        val flavorsMap = Json.fromJson[Map[String, Int]](flavorsJson).getOrElse(Map[String, Int]())
        CoffeeCup(name, roaster, roastDate, producer, brewDate, price, rating, flavorsMap)
      }
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
    println("Inserting")

    val flavorsObj = Json.toJson(coffee.flavors)
    val flavorsJson = Json.stringify(flavorsObj)

    println(flavorsJson)


    DB.withConnection { implicit connection =>
      SQL(
        """
          insert into coffee values (
            (select next value for coffee_seq),
            {name}, 
            {roaster},
            {roastDate},
            {producer}, 
            {brewDate}, 
            {price},
            {rating},
            {flavors}
          )
        """
      ).on(
        'name -> coffee.name,
        'roaster -> coffee.roaster,
        'roastDate -> coffee.roastDate,
        'producer -> coffee.producer,
        'brewDate -> coffee.brewDate,
        'price -> coffee.price,
        'rating -> coffee.rating,
        'flavors -> flavorsJson
      ).executeUpdate()
    }
  }
  
}