/*
 * Copyright 2008 Derek Chen-Becker
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions
 * and limitations under the License.
 */
package org.scala_tools.jpa

import _root_.javax.persistence._

/**
 * This trait defines the operations for obtaining and properly closing
 * an EntityManager. The purpose of this trait is to allow interception
 * and ancillary processing on EM setup and teardown.
 *
 * @author Derek Chen-Becker
 */
trait ScalaEMFactory {
  /**
   * Handles the actual opening of an <code>EntityManager</code>. Subclasses must
   * provide a concrete implementation.
   *
   * @return An appropriately configured <code>EntityManager</code>
   */
  protected def openEM () : EntityManager

  /**
   * Handles closing a previously opened <code>EntityManager</code>. Subclasses must
   * provide a concrete implementation.
   *
   * @param em The <code>EntityManager</code> to close
   */
  protected[jpa] def closeEM (em : EntityManager) : Unit

  /**
   * Returns a newly created <code>ScalaEntityManager</code>
   *
   * @return A new <code>ScalaEntityManager</code> instance.
   */
  def newEM : ScalaEntityManager = {
    val underlying = openEM()
    val owner = this
    new ScalaEntityManager {
      def em = underlying
      val factory = owner
    }
  }
}