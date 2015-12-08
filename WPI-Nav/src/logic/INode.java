package logic;

import javafx.scene.image.ImageView;

import java.util.ArrayList;

/**
 * Created by jacobhackett on 11/24/15.
 */
public interface INode {

    /**
     * getID is used to return the id of the node
     * @param
     * @return double: id of the node
     */
    public int getID();

    /**
     * setID is used to return the id of the node
     *
     * @param
     * @return double: id of the node
     */
    public void setID(int id);


    /**
     * getX is used to return the x-coordinate of the node
     * @param
     * @return double: x-coordinate of the node
     */
    public double getX();

    public void setX(double x_coord);

    /**
     * getY is used to return the y-coordinate of the node
     * @param
     * @return double: y-coordinate of the node
     */
    public double getY();

    public void setY(double y_coord);

    /**
     * getZ is used to return the z-coordinate of the node
     * @return double: z-coordinate of the node
     */
    public double getZ();

    public void setZ(double z_coord);

    /**
     * getG is used to return the g score of the node
     * @return double: g score of the node
     */
    public double getG();

    public void setG(double g_scores);

    /**
     * getH is used to return the h score of the node
     * @return double: h score of the node
     */
    public double getH();

    /**
     * setH is used to change the h score of the node
     *
     * @param h_scores:
     *            a new h score
     * @return void
     */
    public void setH(double h_scores);

    /**
     * getF is used to return the f score of the node
     *
     * @return double: f score of the node
     */
    public double getF();


    public void setF(double f_scores);

    public ArrayList<Edge> getAdjacencies();


    public void setAdjacencies(ArrayList<Edge> adjacencies);

    /**
     * getParent is used to return the parent of this node
     *
     * @return Node: parent of the node
     */
    public INode getParent();

    /**
     * setParent is used to return the parent of this node
     *
     * @return Node: parent of the node
     */
    public void setParent(INode parent);


    public void addEdge(Edge edge);

    /**
     * toString is used to print the node in a readable format
     * @return String: a string describing the node
     */
    public String toString();

    /**
     * getMap_id is used to get the map id of the node
     * @return int: map id of node
     */
    public int getMap_id();

    /**
     * setF is used to change the map_id score of the node
     * @param map_id: a new map id score
     * @return void
     */
    public void setMap_id(int map_id);

    public double getX_univ();

    public double getY_univ();

    public double getZ_univ();

    public void setX_univ(double x_univ);

    public void setY_univ(double y_univ);

    public void setZ_univ(double z_univ);

    public boolean isTransition();

    public boolean isInteresting();

    public boolean isRoom();

    public ArrayList<String> getNames();

    public void removeEdge(int id);

    public ImageView getIcon();

    public String getPicturePath();

    public void setPicturePath(String s);

    public void addNames(ArrayList<String> s);


    }
