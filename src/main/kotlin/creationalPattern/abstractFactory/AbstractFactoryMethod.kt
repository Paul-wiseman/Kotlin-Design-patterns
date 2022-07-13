package creationalPattern.abstractFactory

import org.junit.Assert.*
import org.junit.Test
import org.assertj.core.api.Assertions


/**
 * Abstract Factory method
 *
 * Provides a way to access functionality without caring
 * about implementation
 *
 * It is one level of abstraction over the factory pattern.
 *
 * Separation of concern
 *
 * Allows for testability
 * */

interface DataSource

class DatabaseDataSource: DataSource
class NetworkDataSource: DataSource

abstract class DataSourceFactory{

    abstract fun makeDataSource(): DataSource
    companion object{
        inline fun <reified T: DataSource> createFactory(): DataSourceFactory =
            when(T::class){
              DatabaseDataSource::class -> DatabaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }
}

class NetworkFactory: DataSourceFactory(){
    override fun makeDataSource(): DataSource = NetworkDataSource()
}
class DatabaseFactory: DataSourceFactory(){
    override fun makeDataSource(): DataSource = DatabaseDataSource()
}

class AbstractFactoryMethodTest{
    @Test
    fun afTest(){
        val dataSourceFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val dataSource: DataSource = dataSourceFactory.makeDataSource()
        println("Created datasource :$dataSource")
        Assertions.assertThat(dataSource).isInstanceOf(DatabaseDataSource::class.java )
    }
}