package com.devper.template.core.platform.fcm

import android.os.Bundle
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.*

object BundleJSONConverter {
    private val SETTERS = HashMap<Class<*>, Setter>()

    init {
        SETTERS[Boolean::class.java] = object :
            Setter {
            @Throws(JSONException::class)
            override fun setOnBundle(bundle: Bundle, key: String, value: Any) {
                bundle.putBoolean(key, value as Boolean)
            }

            @Throws(JSONException::class)
            override fun setOnJSON(json: JSONObject, key: String, value: Any) {
                json.put(key, value)
            }
        }
        SETTERS[Int::class.java] = object :
            Setter {
            @Throws(JSONException::class)
            override fun setOnBundle(bundle: Bundle, key: String, value: Any) {
                bundle.putInt(key, value as Int)
            }

            @Throws(JSONException::class)
            override fun setOnJSON(json: JSONObject, key: String, value: Any) {
                json.put(key, value)
            }
        }
        SETTERS[Long::class.java] = object :
            Setter {
            @Throws(JSONException::class)
            override fun setOnBundle(bundle: Bundle, key: String, value: Any) {
                bundle.putLong(key, value as Long)
            }

            @Throws(JSONException::class)
            override fun setOnJSON(json: JSONObject, key: String, value: Any) {
                json.put(key, value)
            }
        }
        SETTERS[Double::class.java] = object :
            Setter {
            @Throws(JSONException::class)
            override fun setOnBundle(bundle: Bundle, key: String, value: Any) {
                bundle.putDouble(key, value as Double)
            }

            @Throws(JSONException::class)
            override fun setOnJSON(json: JSONObject, key: String, value: Any) {
                json.put(key, value)
            }
        }
        SETTERS[String::class.java] = object :
            Setter {
            @Throws(JSONException::class)
            override fun setOnBundle(bundle: Bundle, key: String, value: Any) {
                bundle.putString(key, value as String)
            }

            @Throws(JSONException::class)
            override fun setOnJSON(json: JSONObject, key: String, value: Any) {
                json.put(key, value)
            }
        }
        SETTERS[Array<String>::class.java] = object :
            Setter {
            @Throws(JSONException::class)
            override fun setOnBundle(bundle: Bundle, key: String, value: Any) {
                throw IllegalArgumentException("Unexpected type from JSON")
            }

            @Throws(JSONException::class)
            override fun setOnJSON(json: JSONObject, key: String, value: Any) {
                val jsonArray = JSONArray()
                for (stringValue in value as Array<*>) {
                    jsonArray.put(stringValue)
                }
                json.put(key, jsonArray)
            }
        }

        SETTERS[JSONArray::class.java] = object :
            Setter {
            @Throws(JSONException::class)
            override fun setOnBundle(bundle: Bundle, key: String, value: Any) {
                val jsonArray = value as JSONArray
                val stringArrayList = ArrayList<String>()
                // Empty list, can't even figure out the type, assume an ArrayList<String>
                if (jsonArray.length() == 0) {
                    bundle.putStringArrayList(key, stringArrayList)
                    return
                }

                // Only strings are supported for now
                for (i in 0 until jsonArray.length()) {
                    val current = jsonArray.get(i)
                    if (current is String) {
                        stringArrayList.add(current)
                    } else {
                        throw IllegalArgumentException("Unexpected type in an array: " + current.javaClass)
                    }
                }
                bundle.putStringArrayList(key, stringArrayList)
            }

            @Throws(JSONException::class)
            override fun setOnJSON(json: JSONObject, key: String, value: Any) {
                throw IllegalArgumentException("JSONArray's are not supported in bundles.")
            }
        }
    }

    interface Setter {
        @Throws(JSONException::class)
        fun setOnBundle(bundle: Bundle, key: String, value: Any)

        @Throws(JSONException::class)
        fun setOnJSON(json: JSONObject, key: String, value: Any)
    }

    @Throws(JSONException::class)
    fun convertToJSON(bundle: Bundle): JSONObject {
        val json = JSONObject()

        for (key in bundle.keySet()) {
            val value = bundle.get(key)
                ?: // Null is not supported.
                continue

            // Special case List<String> as getClass would not work, since List is an interface
            if (value is List<*>) {
                val jsonArray = JSONArray()
                for (stringValue in value) {
                    jsonArray.put(stringValue)
                }
                json.put(key, jsonArray)
                continue
            }

            // Special case Bundle as it's one way, on the return it will be JSONObject
            if (value is Bundle) {
                json.put(key, convertToJSON(value))
                continue
            }

            val setter = SETTERS[value.javaClass]
                ?: throw IllegalArgumentException("Unsupported type: " + value.javaClass)
            setter.setOnJSON(json, key, value)
        }

        return json
    }

    @Throws(JSONException::class)
    fun convertToBundle(jsonObject: JSONObject): Bundle {
        val bundle = Bundle()
        val jsonIterator = jsonObject.keys()
        while (jsonIterator.hasNext()) {
            val key = jsonIterator.next()
            val value = jsonObject.get(key)
            if (value == null || value === JSONObject.NULL) {
                // Null is not supported.
                continue
            }

            // Special case JSONObject as it's one way, on the return it would be Bundle.
            if (value is JSONObject) {
                bundle.putBundle(key, convertToBundle(value))
                continue
            }

            val setter = SETTERS[value.javaClass]
                ?: throw IllegalArgumentException("Unsupported type: " + value.javaClass)
            setter.setOnBundle(bundle, key, value)
        }

        return bundle
    }
}