package controllers

import model.Model.{BaseProduct, Product}
import play.api.Play.current
import play.api.cache.Cached
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}
import repository.{ProductNotFoundException, ProductsRepository}
import scala.util.Random

object ProductsController extends Controller {

  import model.Model.Implicits._

  def findAll = Cached("products") {
    Action {
      val products: List[Product] = ProductsRepository.findAll
      Ok(Json.toJson(products))
    }
  }

  def findById(id: Long) = Action.async {
    ProductsRepository.findById(id) map {
      p => Ok(Json.toJson(p))
    } recover {
      case ne: ProductNotFoundException => NotFound
      case _ => InternalServerError
    }
  }

  def findById2(id: Long) = Action.async {
    ProductsRepository.findById2(id) map {
      case Some(p) => Ok(Json.toJson(p))
      case None => NotFound
    } recover {
      case _ => InternalServerError
    }
  }

  def save = Action(parse.json) { implicit request =>
    val baseProduct = Json.fromJson[BaseProduct](request.body).asOpt.get
    val product = Product(Random.nextInt(100), baseProduct.name, baseProduct.price)
    ProductsRepository.save(product)
    Ok(Json.toJson(product))
  }

}
