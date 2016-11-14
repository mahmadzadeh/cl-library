package com.cl.result


class RssItems(val items: Set[ RssItem ] = Nil.toSet) {

    def addSinglePageResult(rssItems: Set[ RssItem ]): RssItems = new RssItems( items ++ rssItems )

    def size: Int = items.size

    def ids: Set[ Long ] = items.map( item => item.adId )

    /**
     * set difference. rss items in this result that are missing
     * in the other result
     */
    def minus(other: RssItems): RssItems = {
        val thisAddIdSet = this.items.collect { case item => item.adId }.toSet
        val thatAddIdSet = other.items.collect { case item => item.adId }.toSet

        // rss ids that are present in this result but missing in other result
        val diff = thisAddIdSet -- thatAddIdSet

        new RssItems( items.filter {
            rssItem => diff.contains( rssItem.adId )
        } )
    }
}
