# Gmage

Gmage is a Groovy-friendly library to process images. You can find the
documentation [here](https://github.com/tambapps/gmage)

I also added methods like

- Gmage.from(Object)  (static method) to instantiate a Gmage quickly from any kind of object handled
- Gmage.toBitmap() to convert a Gmage to a Bitmap
- Gmage.rightShift(Object) to encode a Gmage into an object (File, Path, ...). Groovy 
operator overloading make the syntax simple: `gmage >> file`
- Gmage.show() to display a Gmage directly from the shell