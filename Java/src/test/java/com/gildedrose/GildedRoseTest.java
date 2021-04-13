package com.gildedrose;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GildedRoseTest {

    @Test
    void standardItem_quality_decreases_sellin_decreases_each_day() {
        int startingSellin = 5;
        int startingQuality = 7;
        final Item standardItem = new Item("Elixir of the Mongoose", startingSellin, startingQuality);
        GildedRose app = new GildedRose(new Item[] {standardItem});

        app.updateQuality();

        assertThat(startingSellin - 1).isEqualTo(standardItem.sellIn);
        assertThat(startingQuality - 1).isEqualTo(standardItem.quality);
    }

    @Test
    void multiple_items_degrade_each_day() {
        Item firstItem = new Item("First Standard Item", 5, 4);
        Item secondItem = new Item("Second Standard Item", 3, 2);
        GildedRose subject = new GildedRose(new Item[] { firstItem, secondItem });

        subject.updateQuality();

        assertThat(4).isEqualTo(firstItem.sellIn);
        assertThat(3).isEqualTo(firstItem.quality);
        assertThat(2).isEqualTo(secondItem.sellIn);
        assertThat(1).isEqualTo(secondItem.quality);
    }

    @Test
    void item_quality_degrades_twice_as_fast_passed_sellin_date() {
        Item item = new Item("First Standard Item", -1, 4);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(2).isEqualTo(item.quality);
    }

    @Test
    void item_quality_is_never_negative() {
        Item item = new Item("First Standard Item", 4, 0);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(0).isEqualTo(item.quality);
    }

    @Test
    void aged_items_increase_in_quality_over_time() {
        Item item = new Item("Aged Brie", 5, 6);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(7).isEqualTo(item.quality);
    }

    @Test
    void quality_of_an_item_is_never_greater_than_50() {
        Item item = new Item("Aged Brie", 5, 50);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(50).isEqualTo(item.quality);
    }

    @Test
    void legendary_items_never_have_to_be_sold() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", -1, 80);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(-1).isEqualTo(item.sellIn);
    }

    @Test
    void legendary_items_never_decrease_in_quality() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", -1, 80);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(80).isEqualTo(item.quality);
    }

    @Test
    void backstage_passes_increase_in_quality_as_sellIn_date_approaches() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(21).isEqualTo(item.quality);
    }

    @Test
    void backstage_passes_increase_in_quality_by_2_when_there_are_10_days_or_less() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(22).isEqualTo(item.quality);
    }

    @Test
    void backstage_passes_increase_in_quality_by_3_when_there_are_5_days_or_less() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(23).isEqualTo(item.quality);
    }

    @Test
    void backstage_passes_quality_is_0_after_concert() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(0).isEqualTo(item.quality);
    }
}
