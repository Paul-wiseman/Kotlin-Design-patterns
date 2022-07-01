package adapter

import org.assertj.core.api.Assertions
import org.junit.Test

/**
 * converts the interface of a class into another interface
 * the client expects
 *
 * converts data from one format into another
 *
 * used extensively in android*/

data class DisplayDataType(val index:Float, val data:String)

class DataDisplay{
    fun displayData(data:DisplayDataType){
        println("Data is displayed: ${data.index} - ${data.data}")
    }
}

//----------------------
// Our code

data class DataBaseData(val position:Int, val amount:Int)

class DatabaseDataGenerator{
    fun generateData():List<DataBaseData>{
        val list = arrayListOf<DataBaseData>()
        list.add(DataBaseData(2, 2))
        list.add(DataBaseData(3, 7))
        list.add(DataBaseData(4, 23))
        list.add(DataBaseData(25, 2))
        return list
    }
}

interface DatabaseDataConverter{
    fun convertData(data:List<DataBaseData>):List<DisplayDataType>
}

class DataDisplayAdapter(val display: DataDisplay):DatabaseDataConverter{
    override fun convertData(data: List<DataBaseData>): List<DisplayDataType> {
        val returnList = arrayListOf<DisplayDataType>()
        for (datum in data){
            val ddt = DisplayDataType(datum.position.toFloat(), datum.amount.toString())
            display.displayData(ddt)
            returnList.add(ddt)
        }
        return returnList
    }

}

class AdapterTest{
    @Test
    fun adapterTest(){
        val generator = DatabaseDataGenerator()

        val generatedData = generator.generateData()
        val adaper = DataDisplayAdapter(DataDisplay())
        val convertData = adaper.convertData(generatedData)

        Assertions.assertThat(convertData.size).isEqualTo(4)
        Assertions.assertThat(convertData[0].index).isEqualTo(2f)
        Assertions.assertThat(convertData[1].index).isEqualTo(3f)



    }
}

fun main(){

    charger(powerOutlet().toEUPlug())
    cellPhone(charger(powerOutlet().toEUPlug()).toUsbTypeC())


}
interface UsbTypeC
interface UsbMini


interface EUPlug
interface USPlug

fun powerOutlet():USPlug{
    return object : USPlug{}
}

fun cellPhone(chargeCable:UsbTypeC){

}

// Charger accepts EUPlug interface and exposes UsbMini interface
fun charger(plug: EUPlug) : UsbMini {
    return object : UsbMini {}
}

fun USPlug.toEUPlug():EUPlug{
    return object : EUPlug{
        // Do something to convert
    }
}

fun UsbMini.toUsbTypeC():UsbTypeC{
    return object : UsbTypeC{}
}



