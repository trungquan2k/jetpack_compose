package com.example.jetrandomfood.data.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.jetrandomfood.R

@Entity(tableName="FoodDb")
data class FoodDbModel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "image") val image: Int

) {
    companion object {
        val DEFAULT_POSTS = listOf(
            FoodDbModel(
                1,
                "Mon an mot",
                "1200000",
                image = R.drawable.banhbao
            ),
            FoodDbModel(
                2,
                "Mon an hai",
                "1200000",
                image = R.drawable.banhbao
            ),
            FoodDbModel(
                3,
                "Mon an ba",
                "1200000",
                image = R.drawable.banhbao
            ),
            FoodDbModel(
                4,
                "Mon an bon",
                "1200000",
                image = R.drawable.banhbao
            ),
            FoodDbModel(
                5,
                "Mon an nam",
                "1200000",
                image = R.drawable.banhbao
            ),
        )
    }
}