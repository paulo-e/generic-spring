package com.pauloelienay.slinky.generics

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

open class GenericController<T : IGenericEntity<S>, S>
	(private val business: IGenericBusiness<T, S>) : IGenericController<T, S> {

	@PostMapping
	override fun save(@RequestBody entity: T): ResponseEntity<T> {
		return ResponseEntity.ok(business.save(entity))
	}

	@PutMapping("{id}")
	override fun update(@RequestBody entity: T, @PathVariable id: S): ResponseEntity<T> {
		return business.update(entity, id)
			.let { ResponseEntity.noContent().build() }
	}

	@PatchMapping("{id}")
	override fun updateNonNullFields(@RequestBody entity: T, @PathVariable id: S): ResponseEntity<T> {
		return business.updateNonNullFields(entity, id)
			.let { ResponseEntity.noContent().build() }
	}

	@DeleteMapping("{id}")
	override fun deleteById(@PathVariable id: S): ResponseEntity<Void> {
		return business.deleteById(id)
			.let { ResponseEntity.noContent().build() }
	}

	@GetMapping("{id}")
	override fun getById(@PathVariable id: S): ResponseEntity<T> {
		return ResponseEntity.ok(business.getById(id))
	}

	@RequestMapping("{id}", method = [RequestMethod.HEAD])
	override fun headById(@PathVariable id: S): ResponseEntity<T> {
		return business.headById(id)
			.let { ResponseEntity.ok().build() }
	}
}