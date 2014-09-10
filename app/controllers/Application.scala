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
    def edit(id: String) = Action {
        println(CoffeeCup.findById(id))
        Ok(views.html.index("Your new application is ready."))
    }


    def newCoffee = Action { implicit request =>
        //val coffee_cup = coffeeForm.bindFromRequest

        //println(coffee_cup.data)
        // CoffeeCup.create(name, producer)

        val coffee = CoffeeCup("sample", Option("Ritual"), new Date(), 4)
        // println(name)
        // println(producer)
        // println(rating)
        CoffeeCup.insert(coffee)
        //Home.flashing("success" -> "Computer %s has been created".format(coffee.name))
        Redirect(routes.Application.index).flashing("success" -> "Coffee was created!")
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