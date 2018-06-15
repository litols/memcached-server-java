# memcached-server-java
memcachedライクなIn-memory key value storeのJava8による実装です。
オリジナル機能としてディスクへの書き出しとリストア機能を持っています。
ディスクへの書き出しは変更のあった値のみ書き出します。

memcached-like In-memory key value store implemented with Java. 

## Feature
### Basic feature
- get
- set
- delete
- stats

### Original feature: flush & restore
If receive `flush` command, this program flush memory datas to ./data folder.
In addition, this program restores datas from ./data folder when this program start.

## Requirements

- JDK 1.8 below
- Newline code: support CR+LF only


## Release History

* 0.1.0
initial release

## Credits

Litols – [@litols0816](https://twitter.com/litols0816) – raylitols[at]gmail.com

## License
 
The MIT License (MIT)

Copyright (c) 2018 Litols

Distributed under the MIT license. 

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.