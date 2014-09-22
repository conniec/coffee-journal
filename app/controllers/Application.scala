package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._
import java.util.{Date}

import models.CoffeeCup

object Application extends Controller {
  
    def index = Action {
        Ok(views.html.index("Your new application is ready."))
    }

    // def edit(name: String) = Action {
    //     val coffee = CoffeeCup.findByName(name)
    //     println(CoffeeCup.findByName(name))
    //     Ok(views.html.coffee(coffee))
    // }

    def show(name: String) = Action {
        val coffee = CoffeeCup.findByName(name)
        println(CoffeeCup.findByName(name))
        Ok(views.html.coffee(coffee))
    }

    def all() = Action {
        val coffeeList = CoffeeCup.list("test")
        Ok(views.html.all(coffeeList))
    }

    def add() = Action { implicit request =>
        
        val data = request.body.asFormUrlEncoded.get
        // CoffeeCup.create(name, producer)

        val coffee = CoffeeCup("sample4", Option("Ritual"), new Date(), Option("Kenya"), new Date(), 2, 4, Map[String, Int]("stone_fruit" -> 1, "bitter" -> 2))
        // println(name)
        // println(producer)
        // println(rating)
        CoffeeCup.insert(coffee)
        Ok("testing")
        println("shoudl be done now")
        //Home.flashing("success" -> "Computer %s has been created".format(coffee.name))
        Redirect(routes.Application.all).flashing("message" -> "Coffee was created!")
    }
}