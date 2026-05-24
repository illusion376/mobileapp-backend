package io.illusion.di

import domain.services.LevelServiceImpl
import domain.services.PlayerServiceImpl
import domain.services.contracts.LevelService
import io.illusion.domain.services.contracts.PlayerService
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import java.lang.classfile.Attributes.module

val serverModule = module {
    singleOf(::LevelServiceImpl) bind LevelService::class
    singleOf(::PlayerServiceImpl) bind PlayerService::class
}