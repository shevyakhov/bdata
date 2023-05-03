package com.chelz.bdata

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.chelz.bdata.databinding.ActivityMainBinding
import com.chelz.bdata.ml.Model
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer

class MainActivity : AppCompatActivity() {

	val cities = arrayOf("Albury", "BadgerysCreek", "Cobar", "CoffsHarbour", "Moree", "Newcastle",
						 "NorahHead", "NorfolkIsland", "Penrith", "Richmond", "Sydney", "SydneyAirport",
						 "WaggaWagga", "Williamtown", "Wollongong", "Canberra", "Tuggeranong", "MountGinini",
						 "Ballarat", "Bendigo", "Sale", "MelbourneAirport", "Melbourne", "Mildura",
						 "Nhil", "Portland", "Watsonia", "Dartmoor", "Brisbane", "Cairns",
						 "GoldCoast", "Townsville", "Adelaide", "MountGambier", "Nuriootpa", "Woomera",
						 "Albany", "Witchcliffe", "PearceRAAF", "PerthAirport", "Perth", "SalmonGums",
						 "Walpole", "Hobart", "Launceston", "AliceSprings", "Darwin", "Katherine",
						 "Uluru")
	val intArray = intArrayOf(
		2, 4, 10, 11, 21, 24,
		26, 27, 30, 34, 37, 38,
		42, 45, 47, 9, 40, 23,
		5, 6, 35, 19, 18, 20,
		25, 33, 44, 12, 7, 8,
		14, 39, 0, 22, 28, 48,
		1, 46, 29, 32, 31, 36,
		43, 15, 17, 3, 13, 16,
		41
	)

	val citiesWeight = arrayOf(
		-1.53166617, -1.39110458, -0.96941982, -0.89913902, -0.19633108, 0.0145113,
		0.15507289, 0.22535368, 0.43619606, 0.71731924, 0.92816162, 0.99844241,
		1.27956559, 1.49040797, 1.63096955, -1.03970061, 1.139004, -0.0557695,
		-1.32082378, -1.25054299, 0.78760003, -0.33689267, -0.40717346, -0.26661188,
		0.08479209, 0.64703844, 1.42012717, -0.82885823, -1.1802622, -1.1099814,
		-0.68829664, 1.0687232, -1.67222775, -0.12605029, 0.29563447, 1.70125035,
		-1.60194696, 1.56068876, 0.36591527, 0.57675765, 0.50647685, 0.85788082,
		1.34984638, -0.61801585, -0.47745426, -1.46138537, -0.75857743, -0.54773505,
		1.20928479
	)

	private lateinit var binding: ActivityMainBinding
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		binding.slider.value = 1f

		binding.button.setOnClickListener {
			detect()
		}

		binding.slider.setLabelFormatter {
			val index = intArray.indexOf(it.toInt())
			val string = cities[index]
			string
		}


	}

	fun detect() {
		val index = intArray.indexOf(binding.slider.value.toInt())
		val city = citiesWeight.getOrNull(index) ?: citiesWeight[0]

		val model = Model.newInstance(this)
		val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 26), DataType.FLOAT32)

		val api = Api()
		val floatArray1 = api.getData(city.toFloat())

		val bufferSize = 104
		val byteBuffer = ByteBuffer.allocate(bufferSize)
		for (value in floatArray1) {
			byteBuffer.putFloat(value)
		}
		byteBuffer.rewind()

		inputFeature0.loadBuffer(byteBuffer)
		val outputs = model.process(inputFeature0)
		val outputFeature0 = outputs.outputFeature0AsTensorBuffer.floatArray
		val res = outputFeature0.joinToString(" ").toFloat()
		if (res > 0.0) {
			Toast.makeText(this, "Будет дождь", Toast.LENGTH_SHORT).show()
		} else
			Toast.makeText(this, "Дождя не будет", Toast.LENGTH_SHORT).show()
		model.close()
	}
}