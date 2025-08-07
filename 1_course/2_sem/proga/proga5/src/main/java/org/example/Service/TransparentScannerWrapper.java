package org.example.Service;

import java.io.Closeable;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class TransparentScannerWrapper implements Closeable {

    private final Scanner scanner;
    public final boolean enable_out;

    public TransparentScannerWrapper(Scanner scanner, boolean enable_out) {
        this.scanner = scanner;
        this.enable_out = enable_out;
    }
    public TransparentScannerWrapper(File file, boolean enable_out) throws FileNotFoundException {
        this.scanner = new Scanner(file);
        this.enable_out = enable_out;
    }

    // Методы Scanner, которые мы хотим "прозрачно" предоставить.
    // Важно включить все методы, которые вы хотите использовать.
    // Если метод не включен, его нельзя будет вызвать через оболочку.
    public boolean hasNextFloat() {
        return scanner.hasNextFloat();
    }

    public boolean hasNext() {
        return scanner.hasNext();
    }

    public String next() {
        return scanner.next();
    }

    public boolean hasNextInt() {
        return scanner.hasNextInt();
    }

    public int nextInt() {
        return scanner.nextInt();
    }

    public boolean hasNextDouble() {
        return scanner.hasNextDouble();
    }

    public double nextDouble() {
        return scanner.nextDouble();
    }

    public String nextLine() {
        return scanner.nextLine();
    }

    public boolean hasNextLine() {
        return scanner.hasNextLine();
    }

    public void close() {
        scanner.close();
    }

    public Scanner useDelimiter(Pattern pattern) {
        scanner.useDelimiter(pattern);
        return this.scanner; // Возвращаем оригинальный Scanner, чтобы сохранить цепочку вызовов.
    }

    public Scanner useDelimiter(String pattern) {
        scanner.useDelimiter(pattern);
        return this.scanner; // Возвращаем оригинальный Scanner, чтобы сохранить цепочку вызовов.
    }

    public Scanner reset() {
        scanner.reset();
        return this.scanner; // Возвращаем оригинальный Scanner, чтобы сохранить цепочку вызовов.
    }

    public Locale locale() {
        return scanner.locale();
    }

    public Scanner useLocale(Locale locale) {
        scanner.useLocale(locale);
        return this.scanner; // Возвращаем оригинальный Scanner, чтобы сохранить цепочку вызовов.
    }

    public boolean hasNext(Pattern pattern) {
        return scanner.hasNext(pattern);
    }

    public boolean hasNext(String pattern) {
        return scanner.hasNext(pattern);
    }

    public String next(Pattern pattern) {
        return scanner.next(pattern);
    }

    public String next(String pattern) {
        return scanner.next(pattern);
    }

    public int nextInt(int radix) {
        return scanner.nextInt(radix);
    }

    public int nextInt(int radix, Locale locale) {
        return scanner.nextInt(radix);
    }

    public long nextLong() {
        return scanner.nextLong();
    }

    public long nextLong(int radix) {
        return scanner.nextLong(radix);
    }

    public long nextLong(int radix, Locale locale) {
        return scanner.nextLong(radix);
    }

    public float nextFloat() {
        return scanner.nextFloat();
    }

    public double nextDouble(Locale locale) {
        return scanner.nextDouble();
    }

    public float nextFloat(Locale locale) {
        return scanner.nextFloat();
    }

    public long nextLong(Locale locale) {
        return scanner.nextLong();
    }

    public short nextShort() {
        return scanner.nextShort();
    }

    public short nextShort(int radix) {
        return scanner.nextShort(radix);
    }

    public short nextShort(int radix, Locale locale) {
        return scanner.nextShort(radix);
    }

    public boolean nextBoolean() {
        return scanner.nextBoolean();
    }

    public MatchResult match() {
        return scanner.match();
    }

    public Scanner skip(Pattern pattern) {
        scanner.skip(pattern);
        return this.scanner; // Возвращаем оригинальный Scanner, чтобы сохранить цепочку вызовов.
    }

    public Scanner skip(String pattern) {
        scanner.skip(pattern);
        return this.scanner; // Возвращаем оригинальный Scanner, чтобы сохранить цепочку вызовов.
    }

    public IOException ioException() {
        return scanner.ioException();
    }
}