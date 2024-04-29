// == CS400 Spring 2024 File Header Information ==
// Name: Camilla Liu
// Email: cliu649@wisc.edu
// Lecturer: Florian Heimerl
// Notes to Grader: <optional extra notes>

import java.util.LinkedList;
import java.util.NoSuchElementException;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HashtableMap<KeyType, ValueType> implements MapADT<KeyType, ValueType> {

   protected class Pair {
      public KeyType key;
      public ValueType value;

      public Pair(KeyType key, ValueType value) {
         this.key = key;
         this.value = value;
      }
   }

   protected LinkedList<Pair>[] table;
   protected int size;
   protected int capacity;
   protected static final double LOAD_FACTOR_THRESHOLD = 0.8;

   @SuppressWarnings("unchecked")
   public HashtableMap(int capacity) {
      if (capacity <= 0)
         throw new IllegalArgumentException("Capacity must be positive");
      table = (LinkedList<Pair>[]) new LinkedList<?>[capacity];
      for (int i = 0; i < capacity; i++) {
         table[i] = new LinkedList<Pair>();
      }
      this.capacity = capacity;
      this.size = 0;
   }

   public HashtableMap() {
      this(64); // default capacity = 64
   }

   /**
    * Adds a new key,value pair/mapping to this collection.
    * @param key the key of the key,value pair
    * @param value the value that key maps to
    * @throws IllegalArgumentException if key already maps to a value
    * @throws NullPointerException if key is null
    */
   @Override
   public void put(KeyType key, ValueType value) throws IllegalArgumentException {
      if (key == null)
         throw new NullPointerException("Key cannot be null");
      int index = getIndex(key);
      for (Pair pair : table[index]) {
         if (pair.key.equals(key))
            throw new IllegalArgumentException("Key already exists");
      }
      table[index].add(new Pair(key, value));
      size++;

      // Check and resize if necessary
      if ((double)size / capacity >= LOAD_FACTOR_THRESHOLD) {
         resizeAndRehash();
      }
   }

   private void resizeAndRehash() {
      int newCapacity = capacity * 2;
      LinkedList<Pair>[] newTable = (LinkedList<Pair>[]) new LinkedList<?>[newCapacity];
      for (int i = 0; i < newCapacity; i++) {
         newTable[i] = new LinkedList<Pair>();
      }

      // Rehash all elements
      for (int i = 0; i < capacity; i++) {
         for (Pair pair : table[i]) {
            int newIndex = Math.abs(pair.key.hashCode()) % newCapacity;
            newTable[newIndex].add(pair);
         }
      }

      table = newTable;
      capacity = newCapacity;
   }

   // Helper method to get index for key
   private int getIndex(KeyType key) {
      int hashCode = Math.abs(key.hashCode());
      return hashCode % capacity;
   }

   /**
    * Checks whether a key maps to a value in this collection.
    * @param key the key to check
    * @return true if the key maps to a value, and false is the
    *         key doesn't map to a value
    */
   @Override
   public boolean containsKey(KeyType key) {
      int index = getIndex(key);
      for (Pair pair : table[index]) {
         if (pair.key.equals(key))
            return true;
      }
      return false;
   }

   /**
    * Retrieves the specific value that a key maps to.
    * @param key the key to look up
    * @return the value that key maps to
    * @throws NoSuchElementException when key is not stored in this
    *         collection
    */
   @Override
   public ValueType get(KeyType key) throws NoSuchElementException {
      int index = getIndex(key);
      for (Pair pair : table[index]) {
         if (pair.key.equals(key))
            return pair.value;
      }
      throw new NoSuchElementException("Key not found");
   }

   /**
    * Remove the mapping for a key from this collection.
    * @param key the key whose mapping to remove
    * @return the value that the removed key mapped to
    * @throws NoSuchElementException when key is not stored in this
    *         collection
    */
   @Override
   public ValueType remove(KeyType key) throws NoSuchElementException {
      int index = getIndex(key);
      for (Pair pair : table[index]) {
         if (pair.key.equals(key)) {
            table[index].remove(pair);
            size--;
            return pair.value;
         }
      }
      throw new NoSuchElementException("Key not found");
   }

   /**
    * Removes all key,value pairs from this collection.
    */
   @Override
   public void clear() {
      for (int i = 0; i < capacity; i++) {
         table[i].clear();
      }
      size = 0;
   }

   /**
    * Retrieves the number of keys stored in this collection.
    * @return the number of keys stored in this collection
    */
   @Override
   public int getSize() {
      return size;
   }

   /**
    * Retrieves this collection's capacity.
    * @return the size of te underlying array for this collection
    */
   @Override
   public int getCapacity() {
      return capacity;
   }

   // JUnit5 test methods

   /**
    * Test to check if a key-value pair can be successfully added to the hashtable.
    */
   @Test
   public void testPut() {
      HashtableMap<Integer, String> hashtableMap = new HashtableMap<>();

      hashtableMap.put(1, "One");
      assertTrue(hashtableMap.containsKey(1));
   }

   /**
    * Test to check if the containsKey method correctly identifies the presence of a key in the hashtable.
    */
   @Test
   public void testContainsKey() {
      HashtableMap<Integer, String> hashtableMap = new HashtableMap<>();

      hashtableMap.put(1, "One");
      assertTrue(hashtableMap.containsKey(1));
      assertFalse(hashtableMap.containsKey(2));
   }

   /**
    * Test to check if the get method retrieves the correct value associated with a given key.
    */
   @Test
   public void testGet() {
      HashtableMap<Integer, String> hashtableMap = new HashtableMap<>();

      hashtableMap.put(1, "One");
      hashtableMap.put(2, "Two");

      assertEquals("Two", hashtableMap.get(2));
   }

   /**
    * Test to check if the remove method successfully removes a key-value pair from the hashtable.
    */
   @Test
   public void testRemove() {
      HashtableMap<Integer, String> hashtableMap = new HashtableMap<>();

      hashtableMap.put(1, "One");
      hashtableMap.put(2, "Two");
      hashtableMap.put(3, "Three");
      hashtableMap.put(4, "Four");

      assertEquals(4, hashtableMap.getSize());

      assertEquals("One", hashtableMap.remove(1));
      assertEquals(3, hashtableMap.getSize());

      try {
         hashtableMap.remove(10);
         fail("Expected NoSuchElementException to be thrown");
      } catch (NoSuchElementException e) {
         assertTrue(true);
      }
   }

   /**
    * Test to check if the clear method empties the hashtable.
    */
   @Test
   public void testClear() {
      HashtableMap<Integer, String> hashtableMap = new HashtableMap<>();

      hashtableMap.put(1, "One");
      hashtableMap.put(2, "Two");
      hashtableMap.put(3, "Three");
      hashtableMap.put(4, "Four");

      hashtableMap.clear();

      assertEquals(0, hashtableMap.getSize());
   }
}
