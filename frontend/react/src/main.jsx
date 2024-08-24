import React from 'react'
import ReactDOM from 'react-dom/client'
import Customer from './Customer.jsx'
import {ChakraProvider} from '@chakra-ui/react'
import {createStandaloneToast} from '@chakra-ui/toast'
import {createBrowserRouter, RouterProvider} from "react-router-dom";
import Login from "./components/login/Login.jsx";
import Signup from "./components/signup/Signup.jsx";
import AuthProvider from "./components/context/AuthContext.jsx";
import ProtectedRoute from "./components/shared/ProtectedRoute.jsx";
import './index.css'
import Home from "./Home.jsx";
import Contact from "./components/contacts/Contact.jsx";
import Settings from "./components/settings/Settings.jsx";
import HowItWorks from "./components/howItWorks/HowItWorks.jsx";

const {ToastContainer} = createStandaloneToast();

const router = createBrowserRouter([
    {
        path: "/",
        element: <Login/>
    },
    {
        path: "/signup",
        element: <Signup/>
    },
    {
        path: "homepage",
        element: <ProtectedRoute><Home/></ProtectedRoute>
    },
    {
        path: "homepage/customers",
        element: <ProtectedRoute><Customer/></ProtectedRoute>
    },
    {
        path: "homepage/contact",
        element: <ProtectedRoute><Contact/></ProtectedRoute>
    },
    {
        path: "homepage/settings",
        element: <ProtectedRoute><Settings/></ProtectedRoute>
    },
    {
        path: "homepage/howitworks",
        element: <ProtectedRoute><HowItWorks/></ProtectedRoute>
    }
])

ReactDOM
    .createRoot(document.getElementById('root'))
    .render(
        <React.StrictMode>
            <ChakraProvider>
                <AuthProvider>
                    <RouterProvider router={router}/>
                </AuthProvider>
                <ToastContainer/>
            </ChakraProvider>
        </React.StrictMode>,
    )