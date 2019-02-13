# ProgrammersToolbox_Images

A little helper for work with images. 

Project, Is still work in progress, vis Todos


## Dependency

* Java installed
* maven to run the project

## How to use it

* Download this project
* Build project to obtain jar

### Use command line 

| Param | Description | 
| ------ | ------ | 
| source | **Path** to source file/folder. | 
| output | **Folder**  where result folder with files will be generated, if not given source folder is used. |
| resizeToHeight | Images will be resize to given height in pixels (100 = 100px). |
| resizeToWidth | Images will be resize to given width in pixels (100 = 100px ). |
| resizeByPercent | Images will be resize by given percentage(1 = 100%, 0.5 = 50%, 1.2 = 120% of original size. ) |
| rotate | Image will be rotated by given number of degrees, negative number rotate counterclockwise. |
| watermarkText | Text as watermark in center of image.  |
| imageMods | List of filters tobe apply to the image. |

* Resize will keep width / height ratio to prevent deformation of image, except the case when width and height are both given. 

**Example:**
```sh
java -jar C://Path/to/the/<jar-file-name>.jar source=Path/to/myImagefile.jpg output=Path/to/myresultFolder  resizeByPercent=0.5
```

**List of **imageMods** filters:**

| Param | Description | 
| ------ | ------ | 
| mirror | flip image horizontally | 
| blackAndWhite | image into black & white |
| gray | Image in gray scale |


## To-dos

* Add filter, image as watermark
* Add position for watermark in image (current is center)
* Add filter, vertical flip of image
* At least two waves of refactoring :)
