package model

import play.api.libs.json.Json

object Model {

  case class BaseProduct(name: String)
  case class Product(id: Long, name: String)

  object Implicits {

    implicit val productFormat = Json.format[Product]
    implicit val baseProductFormat = Json.format[BaseProduct]

  }

}
