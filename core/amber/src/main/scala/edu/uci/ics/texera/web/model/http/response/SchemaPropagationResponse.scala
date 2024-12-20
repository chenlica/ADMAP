package edu.uci.ics.texera.web.model.http.response

import edu.uci.ics.amber.core.tuple.Attribute

case class SchemaPropagationResponse(
    code: Int,
    result: Map[String, List[Option[List[Attribute]]]],
    message: String
)
