package com.spring.jwt.data;

public class Views {
    //public static interface ExternalView{}
    //public static interface InternalView{}
    public static interface Post{}
    public static interface PostForAdmin extends Post {}
    public static interface Get {}
    public static interface GetForAdmin extends Get {}
    public static interface Patch {}
    public static interface PatchForAdmin extends Patch {}
    public static interface SignIn {}
}
