package com.tea.landlordapp.dto;

public class BinaryFile {

   public BinaryFile() {
      super();

   }

   public BinaryFile(String name, byte[] content) {
      this.name = name;
      this.content = content;
   }

   private String name;
   private byte[] content;

   public String getName() {
      return name;
   }

   public byte[] getContent() {
      return content;
   }

   public boolean isEmpty() {
      return (content == null || content.length == 0);
   }

   public boolean hasContent() {
      return (content != null && content.length > 0);
   }
}
