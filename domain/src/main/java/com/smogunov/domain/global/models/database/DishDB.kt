package com.smogunov.domain.global.models.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity("dishes")
data class DishDB (
    @PrimaryKey
    val id: Int,
    val name: String,
    val price: Int,
    val weight: Int,
    val description: String,
    @ColumnInfo(name = "image_url")
    val imageUrl: String//,
//    val tags: List<String>
)

@Entity("tags")
data class TagDB(
    @PrimaryKey
    val name: String
)

@Entity(tableName = "dishes_tags", primaryKeys = ["id", "name"])
data class DishTagCrossRefDB(
    val id: Int,
    val name: String
)

data class DishWithTagsDB(
    @Embedded
    val dishDB: DishDB,
    @Relation(parentColumn = "id", entityColumn = "name", associateBy = Junction(DishTagCrossRefDB::class))
    val tagsDB: List<TagDB>
)

data class TagWithDishesDB(
    @Embedded
    val tagDB: TagDB,
    @Relation(parentColumn = "name", entityColumn = "id", associateBy = Junction(DishTagCrossRefDB::class))
    val dishesDB: List<DishDB>
)

