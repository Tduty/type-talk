package info.tduty.typetalk.data.db

import androidx.room.TypeConverter
import info.tduty.typetalk.utils.StringList

/**
 * Created by Evgeniy Mezentsev on 07.03.2020.
 */
class DbConvector {

    @TypeConverter
    fun fromStringsList(stringList: StringList?): String? {
        return stringList?.strings?.joinToString(",")
    }

    @TypeConverter
    fun toStringsList(data: String?): StringList {
        return StringList(data?.split(",") ?: emptyList())
    }
}