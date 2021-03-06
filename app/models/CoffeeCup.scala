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
  
  /**
   * Create a CoffeeCup
   */
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

  /**
   * Retrieve list of coffees
   */
  def list(filter: String = "%"): Seq[CoffeeCup] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          select * from coffee
        """)
        .on()
        .as(CoffeeCup.simple *)
    }
  }


  /**
   * Retrieve a coffee cup from the name.
   */
  def findByName(name: String): Option[CoffeeCup] = {
    DB.withConnection { implicit connection =>
      SQL(
        """
          select * from coffee where name = {name} limit 1
        """)
        .on('name -> name)
        .as(CoffeeCup.simple.singleOpt)
    }
  }


  /**
   * Insert a new cup of coffee.
   *
   * @param coffee The coffee cup values.
   */
  def insert(coffee: CoffeeCup) = {
    println("Inserting")

    val flavorsObj = Json.toJson(coffee.flavors)
    val flavorsJson = Json.stringify(flavorsObj)

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