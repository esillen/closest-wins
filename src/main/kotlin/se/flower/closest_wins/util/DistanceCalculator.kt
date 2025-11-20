package se.flower.closest_wins.util

import kotlin.math.*

object DistanceCalculator {
	private const val EARTH_RADIUS_KM = 6371.0
	
	/**
	 * Calculate the distance between two points on Earth using the Haversine formula
	 * @param lat1 Latitude of first point in degrees
	 * @param lon1 Longitude of first point in degrees
	 * @param lat2 Latitude of second point in degrees
	 * @param lon2 Longitude of second point in degrees
	 * @return Distance in kilometers
	 */
	fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
		val dLat = Math.toRadians(lat2 - lat1)
		val dLon = Math.toRadians(lon2 - lon1)
		
		val lat1Rad = Math.toRadians(lat1)
		val lat2Rad = Math.toRadians(lat2)
		
		val a = sin(dLat / 2).pow(2) + 
				sin(dLon / 2).pow(2) * cos(lat1Rad) * cos(lat2Rad)
		val c = 2 * asin(sqrt(a))
		
		return EARTH_RADIUS_KM * c
	}
}

