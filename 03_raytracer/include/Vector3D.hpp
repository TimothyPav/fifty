#ifndef VECTOR3D_HPP
#define VECTOR3D_HPP

class Vector3D {
    public:
        double x, y, z;

        Vector3D();
        Vector3D(double x, double y, double z);
        
        Vector3D operator+(const Vector3D& v) const;
        Vector3D operator-(const Vector3D& v) const;
        Vector3D operator*(const double scalar) const; // SCALAR MULTIPLICATION
        double dot_product(const Vector3D& v) const;
        Vector3D cross_product(const Vector3D& v) const;
        Vector3D normalize() const;
        double length() const;

        void display_vector() const;
};

#endif
