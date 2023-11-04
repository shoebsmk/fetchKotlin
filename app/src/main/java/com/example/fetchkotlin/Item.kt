package com.example.fetchkotlin

import java.io.Serializable

class Item(val id: String, val listId: String, val name: String) : Comparable<Item>, Serializable {

    override fun toString(): String {
        return "Item{" +
                "id='" + id + '\'' +
                ", listId='" + listId + '\'' +
                ", name='" + name + '\'' +
                '}'
    }

    override fun compareTo(_id: Item): Int {
        return id.compareTo(_id.id)
    }
}