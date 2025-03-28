declare namespace java {
    export interface javaObject {
        toString(): string;
    }
    export interface JavaClass {
        readonly name: string;
    }

}