package com.ralphordanza.budgetup.core.domain

interface DomainMapper <T, DomainModel>  {

    fun mapToDomainModel(model: T): DomainModel

    fun mapFromEntity(domainModel: DomainModel): T
}