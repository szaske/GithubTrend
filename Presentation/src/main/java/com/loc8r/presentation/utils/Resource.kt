/**
 * This class allows me to extend presentation resources and attach a status and message if needed
 */
package com.loc8r.presentation.utils

class Resource<out T> constructor(val status: ResourceState,
                                  val data: T?,
                                  val message: String?)