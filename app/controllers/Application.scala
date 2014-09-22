package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import java.util.{Date}

import models.CoffeeCup

object Application extends Controller {

    // val Home = Redirect(routes.Application.list(0, 2, ""))
  
    def index = Action {
        Ok(views.html.index("Your new application is ready."))
    }



    // val coffeeForm = Form(
    //   mapping(
    //     "name" -> of[String],
    //     "producer" -> of[String],
    //     "rating" -> of[Int]
    //   )(CoffeeCup.apply)(CoffeeCup.unapply)
    // )

    // def coffee = Action {
    //   Ok(views.html.coffee(CoffeeCup.all(), coffeeForm))
    // }
    def edit(name: String) = Action {
        val coffee = CoffeeCup.findByName(name)
        println(CoffeeCup.findByName(name))
        Ok(views.html.coffee(coffee))
    }

    def show(name: String) = Action {
        val coffee = CoffeeCup.findByName(name)
        println(CoffeeCup.findByName(name))
        Ok(views.html.coffee(coffee))
    }

    def list() = Action {
        val coffeeList = CoffeeCup.list("test")
        println(coffeeList)
        Ok(views.html.list(coffeeList))
    }

    def newCoffee = Action { implicit request =>
        
        val data = request.body.asFormUrlEncoded.get

        println("got post data!")
        println(data)
        // CoffeeCup.create(name, producer)

        val coffee = CoffeeCup("sample3", Option("Ritual"), new Date(), Option("Kenya"), new Date(), 2, 4, Map[String, Int]("stone_fruit" -> 1, "bitter" -> 2))
        // println(name)
        // println(producer)
        // println(rating)
        CoffeeCup.insert(coffee)
        Ok(views.html.index("Coffee was created"))
        //Home.flashing("success" -> "Computer %s has been created".format(coffee.name))
       // Redirect(routes.Application.index).flashing("message" -> "Coffee was created!")
    }
  
    // def getCoffee = Action { implicit request =>
    //     //val coffee_cup = coffeeForm.bindFromRequest

    //     //println(coffee_cup.data)
    //     // CoffeeCup.create(name, producer)

    //     val coffee = CoffeeCup("sample", Option("Ritual"), new Date(), 4)
    //     // println(name)
    //     // println(producer)
    //     // println(rating)
    //     CoffeeCup.insert(coffee)
    //     Home.flashing("success" -> "Computer %s has been created".format(computer.name))
    //     //Redirect(routes.Application.coffee)
    // }

}