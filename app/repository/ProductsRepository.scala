package repository

import model.Model.Product

import play.api.libs.concurrent.Execution.Implicits.defaultContext
import scala.collection.mutable.HashMap
import scala.concurrent.Future

case class ProductNotFoundException() extends Exception

object ProductsRepository {

  val products = new HashMap[Long, Product]
  products.put(1, Product(1, "keyboard", 24.37))
  products.put(2, Product(2, "mouse", 5.79))

  def findAll: List[Product] = {
    products.map(p => p._2).toList
  }

  def findById(id: Long): Future[Product] = Future {
    products.get(id).getOrElse(throw new ProductNotFoundException)
  }

  def findById2(id: Long): Future[Option[Product]] = Future {
    products.get(id)
  }

  def save(product: Product): Unit = {
    products.put(product.id, product)
  }

}
