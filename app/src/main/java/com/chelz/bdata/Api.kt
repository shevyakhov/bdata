package com.chelz.bdata

import kotlin.random.Random

class Api {

	var lastCity = 0f
}

fun Api.getData(city: Float): FloatArray {
	val floatArray = floatArrayOf(
		-0.531666f, 0.189757f, -0.044575f, -0.203581f, -0.119472f, 0.14871f,
		1.045228f, 0.307739f, 1.328766f, 1.366458f, 0.673596f, 0.611499f,
		0.111308f, -1.443652f, -1.478015f, -1.223012f, 1.464068f, 0.137693f,
		-0.013506f, 0.019135f, -0.529795f, -1.879575f, -0.016425f, 1.434192f,
		0.278970f, 1.426023f
	)

	val arr = Array(26) { Random(city.hashCode()).nextFloat() }
	return arr.toFloatArray()
}
