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

        assertThat(standardItem.sellIn).isEqualTo(startingSellin - 1);
        assertThat(standardItem.quality).isEqualTo(startingQuality - 1);
    }

    @Test
    void multiple_items_degrade_each_day() {
        Item firstItem = new Item("First Standard Item", 5, 4);
        Item secondItem = new Item("Second Standard Item", 3, 2);
        GildedRose subject = new GildedRose(new Item[] { firstItem, secondItem });

        subject.updateQuality();

        assertThat(firstItem.sellIn).isEqualTo(4);
        assertThat(firstItem.quality).isEqualTo(3);
        assertThat(secondItem.sellIn).isEqualTo(2);
        assertThat(secondItem.quality).isEqualTo(1);
    }

    @Test
    void item_quality_degrades_twice_as_fast_passed_sellin_date() {
        Item item = new Item("First Standard Item", -1, 4);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(item.quality).isEqualTo(2);
    }

    @Test
    void item_quality_is_never_negative() {
        Item item = new Item("First Standard Item", 4, 0);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(item.quality).isEqualTo(0);
    }

    @Test
    void aged_items_increase_in_quality_over_time() {
        Item item = new Item("Aged Brie", 5, 6);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(item.quality).isEqualTo(7);
    }

    @Test
    void quality_of_an_item_is_never_greater_than_50() {
        Item item = new Item("Aged Brie", 5, 50);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(item.quality).isEqualTo(50);
    }

    @Test
    void legendary_items_never_have_to_be_sold() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", -1, 80);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(item.sellIn).isEqualTo(-1);
    }

    @Test
    void legendary_items_never_decrease_in_quality() {
        Item item = new Item("Sulfuras, Hand of Ragnaros", -1, 80);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(item.quality).isEqualTo(80);
    }

    @Test
    void backstage_passes_increase_in_quality_as_sellIn_date_approaches() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(item.quality).isEqualTo(21);
    }

    @Test
    void backstage_passes_increase_in_quality_by_2_when_there_are_10_days_or_less() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 10, 20);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(item.quality).isEqualTo(22);
    }

    @Test
    void backstage_passes_increase_in_quality_by_3_when_there_are_5_days_or_less() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 5, 20);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(item.quality).isEqualTo(23);
    }

    @Test
    void backstage_passes_quality_is_0_after_concert() {
        Item item = new Item("Backstage passes to a TAFKAL80ETC concert", 0, 20);
        GildedRose subject = new GildedRose(new Item[] { item });

        subject.updateQuality();

        assertThat(item.quality).isEqualTo(0);
    }
}
