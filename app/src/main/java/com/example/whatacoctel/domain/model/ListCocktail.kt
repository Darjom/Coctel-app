package com.example.whatacoctel.domain.model

import com.google.gson.annotations.SerializedName

data class ListCockatail (

    @SerializedName("drinks" ) var drinks : ArrayList<Cocktail> = arrayListOf()

) : List<Cocktail> {
    override val size: Int
        get() = TODO("Not yet implemented")

    override fun contains(element: Cocktail): Boolean {
        TODO("Not yet implemented")
    }

    override fun containsAll(elements: Collection<Cocktail>): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(index: Int): Cocktail {
        TODO("Not yet implemented")
    }

    override fun indexOf(element: Cocktail): Int {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun iterator(): Iterator<Cocktail> {
        TODO("Not yet implemented")
    }

    override fun lastIndexOf(element: Cocktail): Int {
        TODO("Not yet implemented")
    }

    override fun listIterator(): ListIterator<Cocktail> {
        TODO("Not yet implemented")
    }

    override fun listIterator(index: Int): ListIterator<Cocktail> {
        TODO("Not yet implemented")
    }

    override fun subList(fromIndex: Int, toIndex: Int): List<Cocktail> {
        TODO("Not yet implemented")
    }
}

